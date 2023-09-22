package com.devtalk.board.consultationboardservice.board.application.port.in;

import com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.PostInput;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostRes;

import java.util.List;

public interface PostUseCase {
    void writePost(PostInput post);

    PostRes viewPost(Long postId);

    List<PostRes> getPostList(Long userId);

    List<PostRes> getAllPosts();

    void modifyPost(Long postId, PostInput postInput);

    void deletePost(Long postId);
}
