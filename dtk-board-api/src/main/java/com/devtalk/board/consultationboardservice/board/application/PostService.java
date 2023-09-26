package com.devtalk.board.consultationboardservice.board.application;

import com.devtalk.board.consultationboardservice.board.application.port.in.PostUseCase;
import com.devtalk.board.consultationboardservice.board.application.port.in.AttachedFileUseCase;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostRes;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostQueryableRepo;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostRepo;
import com.devtalk.board.consultationboardservice.board.domain.attachedfile.AttachedFile;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import com.devtalk.board.consultationboardservice.global.error.ErrorCode;
import com.devtalk.board.consultationboardservice.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.PostInput.*;
import static com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostReq.*;


@Service
@RequiredArgsConstructor
public class PostService implements PostUseCase {
    private final PostQueryableRepo postQueryableRepo;
    private final PostRepo postRepo;
    private final AttachedFileUseCase attachedFileUseCase;

    @Override
    // TODO : 제목, 내용, 작성자에 대한 validation 필요
    public void writePost(PostCreationReq postCreationReq) {
        List<AttachedFile> attachedFileList = attachedFileUseCase.uploadFileList(postCreationReq.getAttachedFileList());
        Post newPost = postCreationReq.toEntity(attachedFileList);
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
    public List<PostRes> getPostsByUserId(Long userId) {
        List<Post> posts = postQueryableRepo.findPostsByUserId(userId);
        return posts.stream().map(post -> PostRes.of(post)).toList();
    }

    @Override
    public List<PostRes> getAllPosts() {
        List<Post> posts = postQueryableRepo.findAllPosts();
        return posts.stream().map(post -> PostRes.of(post)).toList();
    }

    @Override
    @Transactional
    public void modifyPost(Long postId, PostCreationInput postCreationInput) {
        Post post = postQueryableRepo.findPostByPostId(postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_POST));
        post.modify(postCreationInput.getTitle(), postCreationInput.getContent());
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        Post post = postQueryableRepo.findPostByPostId(postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_POST));
        postRepo.delete(post);
    }

    @Override
    public List<PostRes> searchPosts(PostSearchInput postSearchInput) {
        List<Post> posts = postQueryableRepo.findPostsWithSearchOption(postSearchInput);
        return posts.stream().map(post -> PostRes.of(post)).toList();
    }
}
