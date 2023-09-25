package com.devtalk.board.consultationboardservice.board.application.port.in;

import com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.PostInput;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostRes;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;

import java.util.List;

public interface PostUseCase {
    void writePost(PostInput post);

    PostRes viewPost(Long postId);

    List<PostRes> getPostsByUserId(Long userId);

    List<PostRes> getAllPosts();

    void modifyPost(Long postId, PostInput postInput);

    void deletePost(Long postId);

    List<PostRes> searchPosts(PostInput postInput);
}
