package com.djccnt15.spring_board.domain.user.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.mailing.service.EmailService;
import com.djccnt15.spring_board.domain.user.converter.UserConverter;
import com.djccnt15.spring_board.domain.user.model.*;
import com.djccnt15.spring_board.domain.user.service.UserService;
import com.djccnt15.spring_board.exception.UserRecoveryFailedException;
import com.djccnt15.spring_board.utils.MessageTemplateReader;
import com.djccnt15.spring_board.utils.RandomString;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Business
@RequiredArgsConstructor
public class UserBusiness {
    
    private final UserService service;
    private final UserConverter converter;
    private final EmailService emailService;
    private final RandomString randomString;
    private final MessageTemplateReader templateReader;
    
    public void createUser(UserCreateForm form) {
        service.createUser(form);
    }
    
    public void resign(UserSession user) {
        var userEntity = service.getUser(user);
        service.resign(userEntity);
    }
    
    public UserProfile getUserProfile(UserSession user) {
        var entity = service.getUser(user);
        return converter.toProfile(entity);
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
        if (validation) {
            var mailingTemplate = templateReader.getMailingTemplate();
            var password = randomString.generate(12);
            service.recoverUser(user, password);
            emailService.sendEmail(
                user.getEmail(),
                "Spring Board 임시 비밀번호",
                mailingTemplate.formatted(password)
            );
        } else {
            throw new UserRecoveryFailedException("failed to recover user password");
        }
    }
    
    public UserItemListResponse getUserPost(
        UserSession user,
        Integer size,
        Integer page
    ) {
        var postList = service.getUserPost(user, size, page);
        var postListCount = service.getUserPostListCount(user);
        var totalPageCount = (int) Math.ceil((double) postListCount / size);
        return UserItemListResponse.builder()
            .totalPages(totalPageCount)
            .itemList(postList)
            .build();
    }
}
