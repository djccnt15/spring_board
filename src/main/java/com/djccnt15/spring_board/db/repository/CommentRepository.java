package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.dto.CommentProjection;
import com.djccnt15.spring_board.db.dto.UserCommentProjection;
import com.djccnt15.spring_board.db.entity.CommentEntity;
import com.djccnt15.spring_board.db.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    
    @Query(
        value = """
            WITH LatestCommentContent AS (
                SELECT
                    t1.id,
                    t1.created_datetime,
                    t1.content,
                    t1.version,
                    t1.comment_id
                FROM comment_content AS t1
                INNER JOIN (
                    SELECT comment_id, MAX(version) AS max_version
                    FROM comment_content
                    GROUP BY comment_id
                ) AS t2 ON t1.comment_id = t2.comment_id AND t1.version = t2.max_version
            ),
            VoteCounts AS (
                SELECT
                    comment_voter.comment_id,
                    COUNT(*) AS vote_count
                FROM comment_voter
                GROUP BY comment_voter.comment_id
            )
            SELECT
                c.id,
                c.created_datetime,
                cc.created_datetime AS updated_datetime,
                u.username,
                u.id AS user_id,
                cc.content,
                cc.version,
                COALESCE(vote.vote_count, 0) AS vote_count
            FROM comment AS c
            JOIN LatestCommentContent AS cc ON c.id = cc.comment_id
            JOIN user_info AS u ON c.author_id = u.id
            LEFT JOIN VoteCounts AS vote ON c.id = vote.comment_id
            WHERE
                c.is_active = TRUE
                AND c.post_id = :post_id
            ORDER BY c.id DESC
            OFFSET :page ROWS
            FETCH FIRST :size ROWS ONLY
            """,
        nativeQuery = true
    )
    List<CommentProjection> getCommentListByPostId(
        @Param("post_id") Long postId,
        @Param("size") int size,
        @Param("page") int page
    );
    
    List<CommentEntity> findByPostAndIsActive(PostEntity post, boolean isActive);
    
    @Query(value = """
        WITH LatestCommentContent AS (
            SELECT
                t1.id,
                t1.created_datetime,
                t1.content,
                t1.version,
                t1.comment_id
            FROM comment_content AS t1
            INNER JOIN (
                SELECT comment_id, MAX(version) AS max_version
                FROM comment_content
                GROUP BY comment_id
            ) AS t2 ON t1.comment_id = t2.comment_id AND t1.version = t2.max_version
        ),
        Categories AS (
            SELECT
                c.id,
                p.name AS category
            FROM category c
            JOIN category p ON c.parent_id = p.id
        ),
        VoteCounts AS (
            SELECT
                comment_voter.comment_id,
                COUNT(*) AS vote_count
            FROM comment_voter
            GROUP BY comment_voter.comment_id
        )
        SELECT
            c.id,
            c.post_id,
            category.category,
            c.created_datetime,
            cc.created_datetime AS updated_datetime,
            cc.content,
            cc.version,
            COALESCE(vote.vote_count, 0) AS vote_count
        FROM comment AS c
        JOIN post p ON c.post_id = p.id
        JOIN Categories category ON p.category_id = category.id
        JOIN LatestCommentContent AS cc ON c.id = cc.comment_id
        JOIN user_info AS u ON c.author_id = u.id
        LEFT JOIN VoteCounts AS vote ON c.id = vote.comment_id
        WHERE
            c.is_active = TRUE
            AND u.id = :user_id
        ORDER BY c.id DESC
        OFFSET :page ROWS
        FETCH FIRST :size ROWS ONLY
        """,
        nativeQuery = true
    )
    List<UserCommentProjection> getCommentListByUserId(
        @Param("user_id") Long userId,
        @Param("size") int size,
        @Param("page") int page
    );
    
    int countByIsActiveAndAuthorId(boolean isActive, Long userId);
    
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = """
        UPDATE comment
        SET is_active = :is_active
        FROM post p
        JOIN category c ON p.category_id = c.id
        WHERE comment.post_id = p.id
        AND c.parent_id = :category_id
        """,
        nativeQuery = true
    )
    void updateCommentActiveByCategory(
        @Param("is_active") boolean isActive,
        @Param("category_id") Long categoryId
    );
}
