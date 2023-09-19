package com.devtalk.board.consultationboardservice.board.application.port.out.repository;

import com.devtalk.board.consultationboardservice.board.domain.post.Post;

import java.util.List;
import java.util.Optional;

public interface PostQueryableRepo {
    Optional<Post> findPostByPostId(Long postId);

    List<Post> findPostsByUserId(Long userId);
}
