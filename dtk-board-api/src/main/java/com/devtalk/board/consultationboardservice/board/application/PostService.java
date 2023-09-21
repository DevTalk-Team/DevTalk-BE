package com.devtalk.board.consultationboardservice.board.application;

import com.devtalk.board.consultationboardservice.board.application.port.in.PostUseCase;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostRes;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostQueryableRepo;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostRepo;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import com.devtalk.board.consultationboardservice.global.error.ErrorCode;
import com.devtalk.board.consultationboardservice.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.BoardInput.*;

@Service
@RequiredArgsConstructor
public class PostService implements PostUseCase {
    private final PostQueryableRepo postQueryableRepo;
    private final PostRepo postRepo;

    @Override
    // TODO : 제목, 내용, 작성자에 대한 validation 필요
    public void writePost(PostInput postInput) {
        Post newPost = Post.builder()
                .userId(postInput.getUserId())
                .title(postInput.getTitle())
                .content(postInput.getContent())
                .views(0)
                .build();

        postRepo.save(newPost);
    }

    @Override
    @Transactional
    public PostRes viewPost(Long postId) {
        Post post = postQueryableRepo.findPostByPostId(postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_POST));
        post.increaseViews();
        return PostRes.of(post);
    }

    @Override
    public List<PostRes> getPostList(Long userId) {
        List<Post> posts = postQueryableRepo.findPostsByUserId(userId);

        return posts.stream().map(post -> PostRes.of(post)).toList();
    }
}
