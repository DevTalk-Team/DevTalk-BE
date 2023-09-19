package com.devtalk.board.consultationboardservice.board.adapter.out.persistence;

import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostQueryableRepo;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.devtalk.board.consultationboardservice.board.domain.post.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostQueryRepo implements PostQueryableRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Post> findPostByPostId(Long postId) {
        return Optional.ofNullable(
                queryFactory.select(post)
                        .from(post)
                        .where(post.id.eq(postId))
                        .fetchOne()
        );
    }
}
