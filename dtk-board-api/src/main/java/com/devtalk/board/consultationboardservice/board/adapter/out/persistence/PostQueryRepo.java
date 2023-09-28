package com.devtalk.board.consultationboardservice.board.adapter.out.persistence;

import com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.PostInput;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostQueryableRepo;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.PostInput.*;
import static com.devtalk.board.consultationboardservice.board.domain.post.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostQueryRepo implements PostQueryableRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Post> findPostByPostId(Long postId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(post)
                        .where(post.id.eq(postId))
                        .fetchOne()
        );
    }

    @Override
    public List<Post> findPostsByUserId(Long userId) {
        return queryFactory.selectFrom(post)
                .where(post.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<Post> findAllPosts() {
        return queryFactory.selectFrom(post)
                .fetch();
    }

    @Override
    public List<Post> findPostsWithSearchOption(PostSearchInput postInput) {
        return queryFactory.selectFrom(post)
                .where(allSearchOption(postInput))
                .fetch();
    }

    private BooleanBuilder allSearchOption(PostSearchInput postSearchInput) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(postSearchInput.getTitle())) {
            builder.and(titleLike(postSearchInput.getTitle()));
        }

        if (StringUtils.hasText(postSearchInput.getContent())) {
            builder.or(contentLike(postSearchInput.getContent()));
        }

        return builder;
    }

    private BooleanExpression titleLike(String title) {
        return StringUtils.hasText(title) ? post.title.contains(title) : null;
    }

    private BooleanExpression contentLike(String content) {
        return StringUtils.hasText(content) ? post.content.contains(content) : null;
    }
}
