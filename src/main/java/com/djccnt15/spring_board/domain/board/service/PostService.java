package com.djccnt15.spring_board.domain.board.service;

import com.djccnt15.spring_board.db.entity.*;
import com.djccnt15.spring_board.db.repository.CommentRepository;
import com.djccnt15.spring_board.db.repository.PostContentRepository;
import com.djccnt15.spring_board.db.repository.PostRepository;
import com.djccnt15.spring_board.db.repository.PostVoterRepository;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.converter.PostContentConverter;
import com.djccnt15.spring_board.domain.board.converter.PostConverter;
import com.djccnt15.spring_board.domain.board.converter.PostVoterConverter;
import com.djccnt15.spring_board.domain.board.model.*;
import com.djccnt15.spring_board.exception.ApiInvalidAuthorException;
import com.djccnt15.spring_board.exception.DataNotFoundException;
import com.djccnt15.spring_board.exception.ApiForbiddenException;
import com.djccnt15.spring_board.exception.InvalidAuthorException;
import com.djccnt15.spring_board.utils.DownloadFileGenerator;
import com.djccnt15.spring_board.utils.StringUtil;
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
public class PostService {
    
    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final PostContentRepository postContentRepository;
    private final PostContentConverter postContentConverter;
    private final CommentRepository commentRepository;
    private final PostVoterRepository postVoterRepository;
    private final PostVoterConverter postVoterConverter;
    
    public PostEntity createPost(
        UserEntity user,
        CategoryEntity category
    ) {
        var entity = postConverter.toEntity(user, category);
        return postRepository.save(entity);
    }
    
    public void createContent(
        PostEntity post,
        PostCreateRequest request
    ) {
        var entity = postContentConverter.toEntity(request, post);
        postContentRepository.save(entity);
    }
    
    public List<PostDetailResponse> getPostList(
        CategoryEntity mainCategory,
        Integer size,
        Integer page,
        String keyword,
        Long subCategoryId
    ) {
        var postList = postRepository.getPostListByCategory(
            mainCategory.getId(), size, size * page, keyword, subCategoryId
        );
        return postList.stream()
            .map(postConverter::toResponse)
            .toList();
    }
    
    public Integer getPostListCount(
        CategoryEntity category,
        String keyword,
        Long subCategoryId
    ) {
        return postRepository.countPostListByCategory(category.getId(), keyword, subCategoryId);
    }
    
    public List<PostMinimalResponse> getMinimalPostList(
        CategoryEntity category,
        Integer size
    ) {
        var postList = postRepository.getMinimalPostListByCategory(category.getId(), size);
        return postList.stream()
            .map(postConverter::toResponse)
            .toList();
    }
    
    public PostDetailResponse getPostDetail(Long id) {
        var projection = postRepository.getPostDetailById(id)
            .orElseThrow(() -> new DataNotFoundException("can't find data"));
        return postConverter.toResponse(projection);
    }
    
    public PostEntity getPost(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("can't fine requested post"));
    }
    
    public PostContentEntity getLastPostContent(PostEntity post) {
        return postContentRepository.findFirstByPostOrderByIdDesc(post)
            .orElseThrow(() -> new DataNotFoundException("can't fine requested post"));
    }
    
    public void validateAuthor(
        UserSession user,
        PostEntity post
    ) {
        if (!post.getAuthor().getId().equals(user.getUserId())) {
            throw new InvalidAuthorException("invalid author exception");
        }
    }
    
    public void apiValidateAuthor(
        UserSession user,
        PostEntity post
    ) {
        if (!post.getAuthor().getId().equals(user.getUserId())) {
            throw new ApiInvalidAuthorException("invalid author exception");
        }
    }
    
    public void updatePost(
        PostEntity post,
        CategoryEntity category
    ) {
        post.setCategory(category);
        postRepository.save(post);
    }
    
    public void updatePostContent(
        PostEntity post,
        PostContentEntity postContent,
        PostCreateRequest request
    ) {
        var entity = postContentConverter.toEntity(post, postContent, request);
        postContentRepository.save(entity);
    }
    
    public void deletePost(PostEntity post) {
        post.setActive(false);
        postRepository.save(post);
    }
    
    public void validateComment(PostEntity post) {
        var commentList = commentRepository.findByPostAndIsActive(post, true);
        if (!commentList.isEmpty()) {
            throw new ApiForbiddenException("you can't delete commented post");
        }
    }
    
    public Optional<PostVoterEntity> getVoted(
        PostEntity post,
        UserEntity user
    ) {
        return postVoterRepository.findFirstByPostAndUser(post, user);
    }
    
    public void revokeVote(PostVoterEntity postVoter) {
        postVoterRepository.delete(postVoter);
    }
    
    public void votePost(
        PostEntity post,
        UserEntity user
    ) {
        var entity = postVoterConverter.toEntity(post, user);
        postVoterRepository.save(entity);
    }
    
    public List<PostContentEntity> getPostHistory(Long id) {
        return postContentRepository.findByPostIdOrderByIdDesc(id);
    }
    
    public FileResponse createHistoryCsv(List<PostContentHistory> history) {
        var tableName = "PostHistory_%s.csv".formatted(
            StringUtil.datetimeFormatter(LocalDateTime.now(), "yyyyMMdd_HHmmss")
        );
        var tableData = DownloadFileGenerator.generateCsv(history, PostContentHistory.class);
        return FileResponse.builder()
            .fileName(tableName)
            .content(tableData)
            .build();
    }
    
    public void updateViewCount(Long id) {
        var entity = postRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("can't find requested post"));
        entity.setViews(entity.getViews() + 1);
        postRepository.save(entity);
    }
}
