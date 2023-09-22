package com.devtalk.board.consultationboardservice.board.application.port.in;

import com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.CommentInput;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.CommentRes;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostRes;

import java.util.List;

public interface CommentUseCase {
    void createComment(Long postId, CommentInput commentInput);

    CommentRes getComment(Long commentId);

    List<CommentRes> getCommentsFromPost(Long postId);
}
