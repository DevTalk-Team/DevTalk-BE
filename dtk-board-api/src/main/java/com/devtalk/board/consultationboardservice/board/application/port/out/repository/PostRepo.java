package com.devtalk.board.consultationboardservice.board.application.port.out.repository;

import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
}
