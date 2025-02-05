package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.dto.PostDetailProjection;
import com.djccnt15.spring_board.db.dto.PostMinimalProjection;
import com.djccnt15.spring_board.db.dto.UserPostProjection;
import com.djccnt15.spring_board.db.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    
    @Query(
        value = """
            WITH LatestPostContent AS (
                SELECT
                    t1.id,
                    t1.created_datetime,
                    t1.title,
                    t1.content,
                    t1.version,
                    t1.post_id
                FROM post_content AS t1
                INNER JOIN (
                    SELECT post_id, MAX(version) AS max_version
                    FROM post_content
                    GROUP BY post_id
                ) AS t2 ON t1.post_id = t2.post_id AND t1.version = t2.max_version
            ),
            CommentCounts AS (
                SELECT
                    c.post_id,
                    COUNT(*) AS commentCount
                FROM comment c
                WHERE c.is_active = TRUE
                GROUP BY c.post_id
            ),
            VoteCounts AS (
                SELECT
                    post_voter.post_id,
                    COUNT(*) AS vote_count
                FROM post_voter
                GROUP BY post_voter.post_id
            )
            SELECT
                p.id,
                p.created_datetime,
                pc.created_datetime AS updated_datetime,
                u.username,
                u.id AS user_id,
                c.name AS category,
                pc.title,
                pc.content,
                pc.version,
                COALESCE(p.view_count, 0) AS view_count,
                COALESCE(comment.commentCount, 0) AS commentCount,
                COALESCE(vote.vote_count, 0) AS vote_count
            FROM post AS p
            JOIN category c ON p.category_id = c.id
            JOIN user_info u ON p.author_id = u.id
            JOIN LatestPostContent AS pc ON p.id = pc.post_id
            LEFT JOIN CommentCounts AS comment ON p.id = comment.post_id
            LEFT JOIN VoteCounts AS vote ON p.id = vote.post_id
            WHERE
                p.is_active = TRUE
                AND c.parent_id = :category_id
                AND (:sub_category_id IS NULL OR c.id = :sub_category_id)
                AND (
                    u.username LIKE :keyword
                    OR pc.title LIKE :keyword
                    OR pc.content LIKE :keyword
                )
            ORDER BY p.id DESC
            OFFSET :page ROWS
            FETCH FIRST :size ROWS ONLY
            """,
        nativeQuery = true
    )
    List<PostDetailProjection> getPostListByCategory(
        @Param("category_id") Long mainCategoryId,
        @Param("size") Integer size,
        @Param("page") Integer page,
        @Param("keyword") String keyword,
        @Param("sub_category_id") Long subCategoryId
    );
    
    @Query(
        value = """
            WITH LatestPostContent AS (
                SELECT
                    t1.post_id,
                    t1.title,
                    t1.content
                FROM post_content AS t1
                INNER JOIN (
                    SELECT post_id, MAX(version) AS max_version
                    FROM post_content
                    GROUP BY post_id
                ) AS t2 ON t1.post_id = t2.post_id AND t1.version = t2.max_version
            )
            SELECT COUNT(*)
            FROM post AS p
            JOIN category AS c ON p.category_id = c.id
            JOIN user_info u ON p.author_id = u.id
            JOIN LatestPostContent AS pc ON p.id = pc.post_id
            WHERE
                p.is_active = TRUE
                AND c.parent_id = :category_id
                AND (:sub_category_id IS NULL OR c.id = :sub_category_id)
                AND (
                    u.username LIKE :keyword
                    OR pc.title LIKE :keyword
                    OR pc.content LIKE :keyword
                )
            """,
        nativeQuery = true
    )
    Integer countPostListByCategory(
        @Param("category_id") Long categoryId,
        @Param("keyword") String keyword,
        @Param("sub_category_id") Long subCategoryId
    );
    
    @Query(
        value = """
            WITH LatestPostContent AS (
                SELECT
                    t1.post_id,
                    t1.created_datetime,
                    t1.title
                FROM post_content AS t1
                INNER JOIN (
                    SELECT post_id, MAX(version) AS max_version
                    FROM post_content
                    GROUP BY post_id
                ) AS t2 ON t1.post_id = t2.post_id AND t1.version = t2.max_version
            )
            SELECT
                p.id,
                p.created_datetime,
                pc.title
            FROM post AS p
            JOIN category AS c ON p.category_id = c.id
            JOIN LatestPostContent AS pc ON p.id = pc.post_id
            WHERE
                p.is_active = TRUE
                AND c.parent_id = :category_id
            ORDER BY p.id DESC
            FETCH FIRST :size ROWS ONLY
            """,
        nativeQuery = true
    )
    List<PostMinimalProjection> getMinimalPostListByCategory(
        @Param("category_id") Long categoryId,
        @Param("size") Integer size
    );
    
    @Query(
        value = """
            WITH LatestPostContent AS (
                SELECT
                    t1.id,
                    t1.created_datetime,
                    t1.title,
                    t1.content,
                    t1.version,
                    t1.post_id
                FROM post_content AS t1
                INNER JOIN (
                    SELECT post_id, MAX(version) AS max_version
                    FROM post_content
                    GROUP BY post_id
                ) AS t2 ON t1.post_id = t2.post_id AND t1.version = t2.max_version
            ),
            CommentCounts AS (
                SELECT
                    c.post_id,
                    COUNT(*) AS commentCount
                FROM comment c
                WHERE c.is_active = TRUE
                GROUP BY c.post_id
            ),
            VoteCounts AS (
                SELECT
                    post_voter.post_id,
                    COUNT(*) AS vote_count
                FROM post_voter
                GROUP BY post_voter.post_id
            )
            SELECT
                p.id,
                p.created_datetime,
                pc.created_datetime AS updated_datetime,
                u.username,
                u.id AS user_id,
                c.name AS category,
                pc.title,
                pc.content,
                pc.version,
                p.view_count,
                COALESCE(comment.commentCount, 0) AS commentCount,
                COALESCE(vote.vote_count, 0) AS vote_count
            FROM post p
            JOIN LatestPostContent AS pc ON p.id = pc.post_id
            JOIN user_info AS u ON p.author_id = u.id
            JOIN category c ON p.category_id = c.id
            LEFT JOIN CommentCounts AS comment ON p.id = comment.post_id
            LEFT JOIN VoteCounts AS vote ON p.id = vote.post_id
            WHERE
                p.is_active = TRUE
                AND p.ID = :post_id
            """,
        nativeQuery = true
    )
    Optional<PostDetailProjection> getPostDetailById(@Param("post_id") Long postId);
    
    @Query(
        value = """
            WITH LatestPostContent AS (
                SELECT
                    t1.id,
                    t1.created_datetime,
                    t1.title,
                    t1.content,
                    t1.version,
                    t1.post_id
                FROM post_content AS t1
                INNER JOIN (
                    SELECT post_id, MAX(version) AS max_version
                    FROM post_content
                    GROUP BY post_id
                ) AS t2 ON t1.post_id = t2.post_id AND t1.version = t2.max_version
            ),
            Categories AS (
                SELECT
                    c.id,
                    c.name AS sub_category,
                    p.name AS main_category
                FROM category c
                JOIN category p ON c.parent_id = p.id
            ),
            CommentCounts AS (
                SELECT
                    c.post_id,
                    COUNT(*) AS comment_count
                FROM comment c
                WHERE c.is_active = TRUE
                GROUP BY c.post_id
            ),
            VoteCounts AS (
                SELECT
                    post_voter.post_id,
                    COUNT(*) AS vote_count
                FROM post_voter
                GROUP BY post_voter.post_id
            )
            SELECT
                p.id,
                p.created_datetime,
                pc.created_datetime AS updated_datetime,
                c.main_category,
                c.sub_category,
                pc.title,
                pc.content,
                pc.version,
                COALESCE(p.view_count, 0) AS view_count,
                COALESCE(comment.comment_count, 0) AS comment_count,
                COALESCE(vote.vote_count, 0) AS vote_count
            FROM post AS p
            JOIN Categories c ON p.category_id = c.id
            JOIN user_info u ON p.author_id = u.id
            JOIN LatestPostContent AS pc ON p.id = pc.post_id
            LEFT JOIN CommentCounts AS comment ON p.id = comment.post_id
            LEFT JOIN VoteCounts AS vote ON p.id = vote.post_id
            WHERE
                p.is_active = TRUE
                AND u.id = :user_id
            ORDER BY p.id DESC
            OFFSET :page ROWS
            FETCH FIRST :size ROWS ONLY
            """,
        nativeQuery = true
    )
    List<UserPostProjection> getPostListByUserId(
        @Param("user_id") Long userId,
        @Param("size") Integer size,
        @Param("page") Integer page
    );
    
    Integer countByIsActiveAndAuthorId(Boolean isActive, Long userId);
    
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = """
        UPDATE post p
        SET is_active = :is_active
        FROM category c
        WHERE p.category_id = c.id
            AND c.parent_id = :category_id
        """,
        nativeQuery = true
    )
    void updatePostActiveByCategory(
        @Param("is_active") Boolean isActive,
        @Param("category_id") Long categoryId
    );
}
