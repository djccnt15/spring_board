package com.djccnt15.spring_board.domain.board.service;

import com.djccnt15.spring_board.db.entity.CategoryEntity;
import com.djccnt15.spring_board.db.entity.PostContentEntity;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.repository.PostContentRepository;
import com.djccnt15.spring_board.db.repository.PostRepository;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.converter.PostContentConverter;
import com.djccnt15.spring_board.domain.board.converter.PostConverter;
import com.djccnt15.spring_board.domain.board.model.PostMinimalResponse;
import com.djccnt15.spring_board.domain.board.model.PostCreateRequest;
import com.djccnt15.spring_board.domain.board.model.PostDetailResponse;
import com.djccnt15.spring_board.exception.DataNotFoundException;
import com.djccnt15.spring_board.exception.InvalidAuthorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    
    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final PostContentRepository postContentRepository;
    private final PostContentConverter postContentConverter;
    
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
        CategoryEntity category,
        Integer size,
        Integer page,
        String keyword
    ) {
        var postList = postRepository.getPostListByCategory(category.getId(), size, size * page, keyword);
        return postList.stream()
            .map(postConverter::toResponse)
            .toList();
    }
    
    public Integer getPostListCount(
        CategoryEntity category,
        String keyword
    ) {
        return postRepository.countPostListByCategory(category.getId(), keyword);
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
            .orElseThrow(
                () -> new DataNotFoundException("can't find data")
            );
        return postConverter.toResponse(projection);
    }
    
    public PostEntity getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(
            () -> new DataNotFoundException("can't fine requested post")
        );
    }
    
    public PostContentEntity getLastPostContent(PostEntity post) {
        return postContentRepository.findFirstByPostIdOrderByIdDesc(post.getId()).orElseThrow(
            () -> new DataNotFoundException("can't fine requested post")
        );
    }
    
    public void validateAuthor(
        UserSession user,
        PostEntity post
    ) {
        if (!post.getAuthor().getId().equals(user.getUserId())) {
            throw new InvalidAuthorException("invalid author exception");
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
}
