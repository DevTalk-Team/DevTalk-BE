package com.devtalk.board.consultationboardservice.board.application.port.out.repository;

import com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.PostInput;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;

import java.util.List;
import java.util.Optional;

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.PostInput.*;

public interface PostQueryableRepo {
    Optional<Post> findPostByPostId(Long postId);

    List<Post> findPostsByUserId(Long userId);

    List<Post> findAllPosts();

    List<Post> findPostsWithSearchOption(PostSearchInput postSearchInput);
}
