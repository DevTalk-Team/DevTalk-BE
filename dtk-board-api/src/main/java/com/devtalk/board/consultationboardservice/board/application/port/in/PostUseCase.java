package com.devtalk.board.consultationboardservice.board.application.port.in;

import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostRes;

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.BoardInput.*;

public interface PostUseCase {
    void writePost(PostInput post);

    PostRes viewPost(Long postId);
}
