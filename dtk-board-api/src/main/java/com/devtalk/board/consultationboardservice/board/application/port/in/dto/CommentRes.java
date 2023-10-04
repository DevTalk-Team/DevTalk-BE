package com.devtalk.board.consultationboardservice.board.application.port.in.dto;

import com.devtalk.board.consultationboardservice.board.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CommentRes {
    private Long commentId;
    private Long postId;
    private Long userId;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static CommentRes of(Comment comment) {
        return CommentRes.builder()
                .commentId(comment.getId())
                .postId(comment.getPostId().getId())
                .userId(comment.getUserId())
                .userName(comment.getUserName())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
