package com.devtalk.board.consultationboardservice.board.application.port.in.dto;

import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PostRes {
    private String title;
    private String content;
    private Long writerId;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static PostRes of(Post post) {
        return PostRes.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writerId(post.getWriterId())
                .viewCount(post.getViewCount())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }
}
