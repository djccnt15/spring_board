package com.djccnt15.spring_board.domain.board.service;

import com.djccnt15.spring_board.db.entity.*;
import com.djccnt15.spring_board.db.repository.CommentContentRepository;
import com.djccnt15.spring_board.db.repository.CommentRepository;
import com.djccnt15.spring_board.db.repository.CommentVoterRepository;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.converter.CommentContentConverter;
import com.djccnt15.spring_board.domain.board.converter.CommentConverter;
import com.djccnt15.spring_board.domain.board.converter.CommentVoterConverter;
import com.djccnt15.spring_board.domain.board.model.*;
import com.djccnt15.spring_board.exception.DataNotFoundException;
import com.djccnt15.spring_board.exception.InvalidAuthorException;
import com.djccnt15.spring_board.utils.DownloadFileGenerator;
import com.djccnt15.spring_board.utils.StringUtil;
import com.djccnt15.spring_board.utils.model.ExcelCoverData;
import com.djccnt15.spring_board.utils.model.ExcelTableSheetData;
import com.djccnt15.spring_board.utils.model.FileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;
    private final CommentContentRepository commentContentRepository;
    private final CommentContentConverter commentContentConverter;
    private final CommentVoterRepository commentVoterRepository;
    private final CommentVoterConverter commentVoterConverter;
    
    public CommentEntity createComment(
        UserEntity user,
        PostEntity post
    ) {
        var entity = commentConverter.toEntity(user, post);
        return commentRepository.save(entity);
    }
    
    public void createCommentContent(
        CommentEntity comment,
        CommentCreateRequest request
    ) {
        var entity = commentContentConverter.toEntity(request, comment);
        commentContentRepository.save(entity);
    }
    
    public List<CommentResponse> getCommentList(
        PostDetailResponse post,
        int size,
        int page
    ) {
        var commentList = commentRepository.getCommentListByPostId(post.getId(), size, size * page);
        return commentList.stream()
            .map(commentConverter::toResponse)
            .toList();
    }
    
    public CommentEntity getComment(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new DataNotFoundException("can't find requested comment"));
    }
    
    public void validateAuthor(
        UserSession user,
        CommentEntity comment
    ) {
        if (!user.getUserId().equals(comment.getAuthor().getId())) {
            throw new InvalidAuthorException("invalid author exception");
        }
    }
    
    public CommentContentEntity getLastCommentContent(CommentEntity comment) {
        return commentContentRepository.findFirstByCommentOrderByIdDesc(comment)
            .orElseThrow(() -> new DataNotFoundException("can't find requested comment"));
    }
    
    public void updateCommentContent(
        CommentEntity comment,
        CommentContentEntity commentContent,
        CommentCreateRequest request
    ) {
        var entity = commentContentConverter.toEntity(comment, commentContent, request);
        commentContentRepository.save(entity);
    }
    
    public void deleteComment(CommentEntity comment) {
        comment.setActive(false);
        commentRepository.save(comment);
    }
    
    public Optional<CommentVoterEntity> getVoted(
        CommentEntity comment,
        UserEntity user
    ) {
        return commentVoterRepository.findFirstByCommentAndUser(comment, user);
    }
    
    public void revokeVote(CommentVoterEntity commentVoter) {
        commentVoterRepository.delete(commentVoter);
    }
    
    public void voteComment(
        CommentEntity comment,
        UserEntity user
    ) {
        var entity = commentVoterConverter.toEntity(comment, user);
        commentVoterRepository.save(entity);
    }
    
    public List<CommentContentEntity> getCommentHistory(Long id) {
        return commentContentRepository.findByCommentIdOrderByIdDesc(id);
    }
    
    public FileResponse createHistoryCsv(List<CommentContentHistory> history) {
        var tableName = "CommentHistory_%s.csv".formatted(
            StringUtil.datetimeFormatter(LocalDateTime.now(), "yyyyMMdd_HHmmss")
        );
        var tableData = DownloadFileGenerator.generateCsv(history, CommentContentHistory.class);
        return FileResponse.builder()
            .fileName(tableName)
            .content(tableData)
            .build();
    }
    
    public FileResponse createHistoryExcel(
        UserSession user,
        List<CommentContentHistory> history
    ) {
        var sheetName = "CommentHistory_%s".formatted(
            StringUtil.datetimeFormatter(LocalDateTime.now(), "yyyyMMdd_HHmmss")
        );
        var sheetData = ExcelTableSheetData.<CommentContentHistory>builder()
            .records(history)
            .type(CommentContentHistory.class)
            .sheetName(sheetName)
            .build();
        var coverData = ExcelCoverData.builder()
            .title("표지")
            .creator(user.getUsername())
            .createDateTime(StringUtil.datetimeFormatter(LocalDateTime.now(), "yyyyMMdd_HHmmss"))
            .build();
        var tableData = DownloadFileGenerator.generateExcel(coverData, sheetData);
        return FileResponse.builder()
            .fileName("%s.xlsx".formatted(sheetName))
            .content(tableData)
            .build();
    }
}
