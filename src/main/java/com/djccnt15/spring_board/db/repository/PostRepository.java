package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.dto.PostSummary;
import com.djccnt15.spring_board.db.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    
    @Query(
        value = """
            SELECT
                p.id,
                p.created_datetime,
                pc.created_datetime AS updated_datetime,
                u.username,
                pc.title,
                pc.content,
                comment.commentCount,
                vote.voteCount
            FROM post AS p
            JOIN category c ON p.category_id = c.id
            JOIN user_info u ON p.author_id = u.id
            JOIN (
                SELECT
                    t1.id,
                    t1.created_datetime,
                    t1.title,
                    t1.content,
                    t1.post_id
                FROM post_content AS t1
                JOIN (
                    SELECT post_id, MAX(version) AS max_version
                    FROM post_content
                    GROUP BY post_id
                    ) AS t2 ON t1.post_id = t2.post_id AND t1.version = t2.max_version
            ) AS pc ON p.id = pc.post_id
            LEFT JOIN (
                SELECT c.post_id, COUNT(*) AS commentCount
                FROM comment c
                WHERE
                    c.is_active = TRUE
                GROUP BY post_id
            ) AS comment ON p.id = comment.post_id
            LEFT JOIN (
                SELECT
                    post_voter.post_id,
                    count(*) AS voteCount
                FROM post_voter
                GROUP BY post_voter.post_id
            ) AS vote ON p.id = vote.post_id
            WHERE
                p.is_active = TRUE
                AND (
                    c.parent_id = :category_id
                    OR c.id = :category_id
                )
                AND (
                    u.username LIKE :keyword
                    OR title LIKE :keyword
                    OR content LIKE :keyword
                )
            ORDER BY p.id DESC
            LIMIT :size OFFSET :page
            """,
        nativeQuery = true
    )
    List<PostSummary> findPostListByCategory(
        @Param("category_id") long categoryId,
        @Param("size") int size,
        @Param("page") int page,
        @Param("keyword") String keyword
    );
    
    @Query(
        value = """
            SELECT COUNT(*)
            FROM post AS p
            JOIN category AS c ON p.category_id = c.id
            JOIN (
                SELECT
                    t1.id,
                    t1.created_datetime,
                    t1.title,
                    t1.content,
                    t1.post_id
                FROM post_content AS t1
                JOIN (
                    SELECT
                        post_id,
                        MAX(version) AS max_version
                    FROM post_content
                    GROUP BY post_id
                    ) AS t2 ON t1.post_id = t2.post_id AND t1.version = t2.max_version
            ) AS pc ON p.id = pc.post_id
            WHERE
                p.is_active = TRUE
                AND (
                    c.parent_id = :category_id
                    OR c.id = :category_id
                )
                AND (
                    title LIKE :keyword
                    OR content LIKE :keyword
                )
            """,
        nativeQuery = true
    )
    int countPostListByCategory(
        @Param("category_id") long categoryId,
        @Param("keyword") String keyword
    );
}
