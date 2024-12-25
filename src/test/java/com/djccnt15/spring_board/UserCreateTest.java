package com.djccnt15.spring_board;

import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserCreateTest {
    
    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private UserRepository repository;
    
    @Test
    void testJpa() {
        for (int i = 0; i < 100; i++) {
            var user = UserEntity.builder()
                .username("test_%d".formatted(i))
                .password(encoder.encode("test"))
                .email("test_%d@b.com".formatted(i))
                .build();
            
            repository.save(user);
        }
    }
}
