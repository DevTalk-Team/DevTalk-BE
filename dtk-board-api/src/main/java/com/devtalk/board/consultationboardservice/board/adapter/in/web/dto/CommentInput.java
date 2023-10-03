package com.devtalk.board.consultationboardservice.board.adapter.in.web.dto;

import com.devtalk.board.consultationboardservice.board.application.port.in.dto.CommentReq;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.CommentRes;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

import static com.devtalk.board.consultationboardservice.board.application.port.in.dto.CommentReq.*;

public class CommentInput{

    @Getter
    @Builder
    public static class CommentCreationInput{
        private Long postId;
        private String content;

        public CommentCreationReq toReq(Long userId) {
            return CommentCreationReq.builder()
                    .userId(userId)
                    .postId(postId)
                    .content(content)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class CommentModifyInput{
        private Long commentId;
        private String content;

        public CommentModifyReq toReq(Long userId) {
            return CommentModifyReq.builder()
                    .userId(userId)
                    .commentId(commentId)
                    .content(content)
                    .build();
        }
    }

}
