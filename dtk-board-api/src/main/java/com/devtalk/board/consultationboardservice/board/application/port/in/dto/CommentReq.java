package com.devtalk.board.consultationboardservice.board.application.port.in.dto;

import com.devtalk.board.consultationboardservice.board.domain.comment.Comment;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

public class CommentReq {
    @Getter
    @Builder
    public static class CommentCreationReq{
        private Long userId;
        private Long postId;
        private String content;

        public Comment toEntity(Post post, String userName) {
            return Comment.createComment(post, userId, userName, content);
        }
    }

    @Getter
    @Builder
    public static class CommentModifyReq{
        private Long commentId;
        private Long userId;
        private String content;
    }
}
