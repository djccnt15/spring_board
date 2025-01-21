package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.dto.CommentProjection;
import com.djccnt15.spring_board.db.entity.CommentEntity;
import com.djccnt15.spring_board.db.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
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
            """,
        nativeQuery = true
    )
    List<CommentProjection> getCommentListByPostId(@Param("post_id") Long postId);
    
    List<CommentEntity> findByPostAndIsActive(PostEntity post, Boolean isActive);
}
