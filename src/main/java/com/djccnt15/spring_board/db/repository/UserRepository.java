package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findFirstByUsername(String username);
    
    @Query("""
        SELECT u
        FROM UserEntity u
        WHERE 1=1
            AND u.username IS NOT NULL
            AND (
                LOWER(u.username) LIKE LOWER(:kw)
                OR LOWER(u.email) LIKE LOWER(:kw)
            )
        ORDER BY u.id
        """
    )
    Page<UserEntity> findByKeywordAndUsernameIsNotNull(Pageable pageable, @Param("kw") String Keyword);
    
    Optional<UserEntity> findFirstByUsernameAndIdNot(String username, Long id);
    
    Optional<UserEntity> findFirstByEmailAndIdNot(String email, Long id);
    
    Optional<UserEntity> findFirstById(Long id);
}
