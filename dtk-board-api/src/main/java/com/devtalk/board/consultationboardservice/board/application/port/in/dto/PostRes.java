package com.devtalk.board.consultationboardservice.board.application.port.in.dto;

import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class PostRes {
    @Builder
    @Getter
    public static class PostViewRes{
        private Long postId;
        private String title;
        private String content;
        private List<AttachedFileRes> attachedFileResList;
        private List<CommentRes> commentResList;
        private Long userId;
        private String userName;
        private Integer views;
        private Integer commentCount;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public static PostViewRes of(Post post, List<AttachedFileRes> attachedFileRes,
                                     List<CommentRes> commentResList) {
            return PostViewRes.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .attachedFileResList(attachedFileRes)
                    .commentResList(commentResList)
                    .userId(post.getUserId())
                    .userName(post.getUserName())
                    .views(post.getViews())
                    .commentCount(post.getCommentCount())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class PostSearchRes {
        private Long postId;
        private String title;
        private String content;
        private Long userId;
        private String userName;
        private Integer views;
        private Integer commentCount;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public static PostSearchRes of(Post post) {
            return PostSearchRes.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .userId(post.getUserId())
                    .userName(post.getUserName())
                    .views(post.getViews())
                    .commentCount(post.getCommentCount())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build();
        }
    }
}
