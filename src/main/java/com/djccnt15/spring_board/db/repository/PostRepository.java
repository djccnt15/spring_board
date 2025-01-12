package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.dto.PostDetailProjection;
import com.djccnt15.spring_board.db.dto.PostMinimalProjection;
import com.djccnt15.spring_board.db.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
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
                    COUNT(*) AS voteCount
                FROM post_voter
                GROUP BY post_voter.post_id
            )
            SELECT
                p.id,
                p.created_datetime,
                pc.created_datetime AS updated_datetime,
                u.username,
                pc.title,
                pc.content,
                pc.version,
                COALESCE(comment.commentCount, 0) AS commentCount,
                COALESCE(vote.voteCount, 0) AS voteCount
            FROM post AS p
            JOIN category c ON p.category_id = c.id
            JOIN user_info u ON p.author_id = u.id
            JOIN LatestPostContent AS pc ON p.id = pc.post_id
            LEFT JOIN CommentCounts AS comment ON p.id = comment.post_id
            LEFT JOIN VoteCounts AS vote ON p.id = vote.post_id
            WHERE
                p.is_active = TRUE
                AND (
                    c.parent_id = :category_id
                    OR c.id = :category_id
                )
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
        @Param("category_id") long categoryId,
        @Param("size") int size,
        @Param("page") int page,
        @Param("keyword") String keyword
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
            JOIN LatestPostContent AS pc ON p.id = pc.post_id
            WHERE
                p.is_active = TRUE
                AND (
                    c.parent_id = :category_id
                    OR c.id = :category_id
                )
                AND (
                    pc.title LIKE :keyword
                    OR pc.content LIKE :keyword
                )
            """,
        nativeQuery = true
    )
    int countPostListByCategory(
        @Param("category_id") long categoryId,
        @Param("keyword") String keyword
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
        @Param("category_id") long categoryId,
        @Param("size") int size
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
                    COUNT(*) AS voteCount
                FROM post_voter
                GROUP BY post_voter.post_id
            )
            SELECT
                p.id,
                p.created_datetime,
                pc.created_datetime AS updated_datetime,
                u.username,
                pc.title,
                pc.content,
                pc.version,
                COALESCE(comment.commentCount, 0) AS commentCount,
                COALESCE(vote.voteCount, 0) AS voteCount
            FROM post p
            JOIN LatestPostContent AS pc ON p.id = pc.post_id
            JOIN user_info AS u ON p.author_id = u.id
            LEFT JOIN CommentCounts AS comment ON p.id = comment.post_id
            LEFT JOIN VoteCounts AS vote ON p.id = vote.post_id
            WHERE
                p.is_active = TRUE
                AND p.ID = :post_id
            """,
        nativeQuery = true
    )
    Optional<PostDetailProjection> getPostDetail(@Param("post_id") Long postId);
}
