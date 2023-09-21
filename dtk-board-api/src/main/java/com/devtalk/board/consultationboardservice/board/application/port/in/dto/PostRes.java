package com.devtalk.board.consultationboardservice.board.application.port.in.dto;

import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PostRes {
    private Long postId;
    private String title;
    private String content;
    private Long userId;
    private Integer views;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static PostRes of(Post post) {
        return PostRes.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUserId())
                .views(post.getViews())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }
}
