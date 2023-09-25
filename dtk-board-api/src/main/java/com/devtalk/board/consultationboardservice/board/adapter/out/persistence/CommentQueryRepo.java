package com.devtalk.board.consultationboardservice.board.adapter.out.persistence;

import com.devtalk.board.consultationboardservice.board.application.port.out.repository.CommentQueryableRepo;
import com.devtalk.board.consultationboardservice.board.domain.comment.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.devtalk.board.consultationboardservice.board.domain.comment.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepo implements CommentQueryableRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Comment> findByCommentId(Long commentId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(comment)
                        .where(comment.id.eq(commentId))
                        .fetchOne()
        );
    }

    @Override
    public List<Comment> findCommentsByPostId(Long postId) {
        return queryFactory.selectFrom(comment)
                .where(comment.postId.id.eq(postId))
                .fetch();
    }
}
