package com.devtalk.board.consultationboardservice.board.application.port.out.repository;

import com.devtalk.board.consultationboardservice.board.domain.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentQueryableRepo {
    Optional<Comment> findByCommentId(Long commentId);

    List<Comment> findCommentsByPostId(Long postId);

}
