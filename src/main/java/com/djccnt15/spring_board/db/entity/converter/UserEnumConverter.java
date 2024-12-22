package com.djccnt15.spring_board.db.entity.converter;

import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserEnumConverter implements AttributeConverter<UserRoleEnum, String> {
    @Override
    public String convertToDatabaseColumn(UserRoleEnum role) {
        if (role == null) {
            return null;
        }
        return role.getRole();
    }
    
    @Override
    public UserRoleEnum convertToEntityAttribute(String role) {
        if (role == null) {
            return null;
        }
        
        return Stream.of(UserRoleEnum.values())
            .filter(it -> it.getRole().equals(role))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
