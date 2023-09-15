package com.devtalk.board.consultationboardservice.board.application;

import com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.BoardInput;
import com.devtalk.board.consultationboardservice.board.application.port.in.PostUseCase;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostReq;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostRepo;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.BoardInput.*;

@Service
@RequiredArgsConstructor
public class PostService implements PostUseCase {
    private final PostRepo postRepo;

    @Override
    // TODO : 제목, 내용, 작성자에 대한 validation 필요
    public void writePost(PostInput postInput) {
        Post newPost = Post.builder()
                .writer(postInput.getWriter())
                .title(postInput.getTitle())
                .content(postInput.getContent())
                .viewCount(0)
                .build();

        postRepo.save(newPost);
    }
}
