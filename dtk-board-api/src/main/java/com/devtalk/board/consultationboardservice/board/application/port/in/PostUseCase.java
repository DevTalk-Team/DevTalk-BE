package com.devtalk.board.consultationboardservice.board.application.port.in;

import com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.PostInput;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostReq;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostRes;

import java.util.List;

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.PostInput.*;
import static com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostReq.*;

public interface PostUseCase {
    void writePost(PostCreationReq postCreationReq);

    PostRes viewPost(Long postId);

    List<PostRes> getPostsByUserId(Long userId);

    List<PostRes> getAllPosts();

    void modifyPost(Long postId, PostCreationInput postCreationInput);

    void deletePost(Long postId);

    List<PostRes> searchPosts(PostSearchInput postSearchInput);
}
