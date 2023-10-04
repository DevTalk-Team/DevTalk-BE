package com.devtalk.board.consultationboardservice.board.application.port.in;

import java.util.List;

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.PostInput.*;
import static com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostReq.*;
import static com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostRes.*;

public interface PostUseCase {
    void writePost(PostCreationReq postCreationReq);

    PostViewRes viewPost(Long postId);

    List<PostSearchRes> getPostsByUserId(Long userId);

    List<PostSearchRes> getAllPosts();

    void modifyPost(Long postId, PostModifyReq postModifyReq);

    void deletePost(Long postId, Long userId);

    List<PostSearchRes> searchPosts(PostSearchInput postSearchInput);
}
