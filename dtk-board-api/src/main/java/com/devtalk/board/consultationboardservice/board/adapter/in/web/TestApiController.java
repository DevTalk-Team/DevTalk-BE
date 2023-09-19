package com.devtalk.board.consultationboardservice.board.adapter.in.web;

import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostRepo;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RefreshScope
public class TestApiController {
    private final PostRepo postRepo;

    @Value("${health_check}")
    String healthStatus;

    @GetMapping("/config/health_check")
    public String healthCheck(){
        return healthStatus;
    }

    @PostMapping("/bulk")
    public void bulk(){
        for (long i = 0; i < 100; i++) {
            Post newPost = Post.builder()
                    .userId(i)
                    .title(i + "번째 게시물")
                    .content(i + "번째 게시물 내용")
                    .viewCount(0)
                    .build();

            postRepo.save(newPost);
        }
    }
}
