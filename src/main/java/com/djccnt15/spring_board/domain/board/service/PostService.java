package com.djccnt15.spring_board.domain.board.service;

import com.djccnt15.spring_board.db.entity.CategoryEntity;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.repository.PostContentRepository;
import com.djccnt15.spring_board.db.repository.PostRepository;
import com.djccnt15.spring_board.domain.board.converter.PostContentConverter;
import com.djccnt15.spring_board.domain.board.converter.PostConverter;
import com.djccnt15.spring_board.domain.board.model.PostMinimalResponse;
import com.djccnt15.spring_board.domain.board.model.PostCreateRequest;
import com.djccnt15.spring_board.domain.board.model.PostDetailResponse;
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
        int size,
        int page,
        String keyword
    ) {
        var postList = postRepository.getPostListByCategory(category.getId(), size, size * page, keyword);
        return postList.stream()
            .map(postConverter::toResponse)
            .toList();
    }
    
    public int getPostListCount(
        CategoryEntity category,
        String keyword
    ) {
        return postRepository.countPostListByCategory(category.getId(), keyword);
    }
    
    public List<PostMinimalResponse> getMinimalPostList(
        CategoryEntity category,
        int size
    ) {
        var postList = postRepository.getMinimalPostListByCategory(category.getId(), size);
        return postList.stream()
            .map(postConverter::toResponse)
            .toList();
    }
}
