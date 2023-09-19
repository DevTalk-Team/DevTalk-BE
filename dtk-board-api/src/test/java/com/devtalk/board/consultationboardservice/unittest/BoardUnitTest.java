package com.devtalk.board.consultationboardservice.unittest;

import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostRepo;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardUnitTest {
    private final PostRepo postRepo;

    @Autowired
    public BoardUnitTest(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @Test
    void bulkData() {
        for (long i = 1; i < 100; i++) {
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
