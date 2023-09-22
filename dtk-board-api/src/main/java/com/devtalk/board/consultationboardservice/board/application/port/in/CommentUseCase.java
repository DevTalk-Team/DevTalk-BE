package com.devtalk.board.consultationboardservice.board.application.port.in;

import com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.BoardInput;

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.BoardInput.*;

public interface CommentUseCase {
    void createComment(Long postId, CommentInput commentInput);
}
