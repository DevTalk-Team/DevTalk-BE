package com.devtalk.board.consultationboardservice.board.application.port.in;

import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostRes;

import java.util.List;

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.BoardInput.*;

public interface PostUseCase {
    void writePost(PostInput post);

    PostRes viewPost(Long postId);

    List<PostRes> getPostList(Long userId);
}
