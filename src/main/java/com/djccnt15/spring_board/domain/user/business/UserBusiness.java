package com.djccnt15.spring_board.domain.user.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.mailing.service.EmailService;
import com.djccnt15.spring_board.domain.user.converter.UserConverter;
import com.djccnt15.spring_board.domain.user.model.*;
import com.djccnt15.spring_board.domain.user.service.UserService;
import com.djccnt15.spring_board.exception.FormValidationException;
import com.djccnt15.spring_board.utils.CommonUtil;
import com.djccnt15.spring_board.utils.MessageTemplateReader;
import com.djccnt15.spring_board.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Business
@RequiredArgsConstructor
public class UserBusiness {
    
    private final UserService service;
    private final UserConverter converter;
    private final EmailService emailService;
    private final MessageTemplateReader templateReader;
    
    public void createUser(UserCreateForm form) {
        service.createUser(form);
    }
    
    public void resign(UserSession user) {
        var validation = service.validateAdmin(user);
        if (validation) {
            throw new RuntimeException("NEVER DELETE ADMIN USER");
        }
        var userEntity = service.getUser(user);
        service.resign(userEntity);
    }
    
    public UserResponse getUserInfo(UserSession user) {
        var entity = service.getUser(user);
        return converter.toResponse(entity);
    }
    
    public void updateProfile(
        UserSession user,
        UserUpdateForm form
    ) {
        var userEntity = service.validateUpdateForm(user, form);
        service.updateUser(userEntity, form);
    }
    
    @Transactional  // rollback changed password if sending email is failed
    public void recoverUser(UserRecoveryForm form) {
        var user = service.getUser(form.getUsername());
        var validation = service.validateRecoverEmail(form, user);
        if (!validation) {
            throw new FormValidationException("incorrect user information");
        }
        var mailingTemplate = templateReader.getMailingTemplate();
        var password = StringUtil.generateRandomString(12);
        service.recoverUser(user, password);
        emailService.sendEmail(
            user.getEmail(),
            "Spring Board 임시 비밀번호",
            mailingTemplate.formatted(password)
        );
    }
    
    public UserItemListResponse getUserPost(
        UserSession user,
        int size,
        int page
    ) {
        var postList = service.getUserPost(user, size, page);
        var postListCount = service.getUserPostListCount(user);
        var totalPageCount = CommonUtil.getTotalPageCount(postListCount, size);
        return UserItemListResponse.builder()
            .totalPages(totalPageCount)
            .itemList(postList)
            .build();
    }
    
    public UserItemListResponse getUserComment(
        UserSession user,
        int size,
        int page
    ) {
        var commentList = service.getUserComment(user, size, page);
        var commentListCount = service.getUserCommentListCount(user);
        var totalPageCount = CommonUtil.getTotalPageCount(commentListCount, size);
        return UserItemListResponse.builder()
            .totalPages(totalPageCount)
            .itemList(commentList)
            .build();
    }
    
    public UserInfoItemResponse getUserInfoPostLIst(
        Long id,
        int size,
        int page
    ) {
        var userEntity = service.getUser(id);
        var user = converter.toResponse(userEntity);
        service.setState(user);
        var postList = service.getUserPost(userEntity, size, page);
        var postListCount = service.getUserPostListCount(userEntity);
        var totalPageCount = CommonUtil.getTotalPageCount(postListCount, size);
        var itemList = UserItemListResponse.builder()
            .totalPages(totalPageCount)
            .itemList(postList)
            .build();
        return UserInfoItemResponse.builder()
            .user(user)
            .items(itemList)
            .build();
    }
    
    public UserInfoItemResponse getUserInfoCommentLIst(
        Long id,
        int size,
        int page
    ) {
        var userEntity = service.getUser(id);
        var user = converter.toResponse(userEntity);
        service.setState(user);
        var commentList = service.getUserComment(userEntity, size, page);
        var commentListCount = service.getUserCommentListCount(userEntity);
        var totalPageCount = CommonUtil.getTotalPageCount(commentListCount, size);
        var itemList = UserItemListResponse.builder()
            .totalPages(totalPageCount)
            .itemList(commentList)
            .build();
        return UserInfoItemResponse.builder()
            .user(user)
            .items(itemList)
            .build();
    }
}
