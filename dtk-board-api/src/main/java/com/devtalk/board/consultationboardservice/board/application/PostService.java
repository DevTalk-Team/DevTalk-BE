package com.devtalk.board.consultationboardservice.board.application;

import com.devtalk.board.consultationboardservice.board.application.port.in.CommentUseCase;
import com.devtalk.board.consultationboardservice.board.application.port.in.PostUseCase;
import com.devtalk.board.consultationboardservice.board.application.port.in.AttachedFileUseCase;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.MemberRes;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostRes;
import com.devtalk.board.consultationboardservice.board.application.port.out.client.MemberServiceClient;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.AttachedFileQueruableRepo;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.AttachedFileRepo;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostQueryableRepo;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostRepo;
import com.devtalk.board.consultationboardservice.board.application.validator.BoardValidator;
import com.devtalk.board.consultationboardservice.board.domain.attachedfile.AttachedFile;
import com.devtalk.board.consultationboardservice.board.domain.comment.Comment;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import com.devtalk.board.consultationboardservice.global.error.ErrorCode;
import com.devtalk.board.consultationboardservice.global.error.exception.NotFoundException;
import com.devtalk.board.consultationboardservice.global.vo.BaseFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.PostInput.*;
import static com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostReq.*;
import static com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostRes.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostService implements PostUseCase {
    private final PostQueryableRepo postQueryableRepo;
    private final PostRepo postRepo;
    private final AttachedFileRepo attachedFileRepo;
    private final AttachedFileUseCase attachedFileUseCase;
    private final CommentUseCase commentUseCase;
    private final BoardValidator boardValidator;
    private final MemberServiceClient memberServiceClient;

    @Override
    @Transactional
    // TODO : 제목, 내용, 작성자에 대한 validation 필요
    public void writePost(PostCreationReq postCreationReq) {
        boardValidator.validatePost(postCreationReq);
        //
        MemberRes memberRes = memberServiceClient.findMember(postCreationReq.getUserId());
        log.info(memberRes.toString());

        Post newPost = postCreationReq.toEntity(); // 초기 빈 리스트와 함께 Post 생성
        newPost = postRepo.save(newPost);

        if (postCreationReq.getAttachedFileList() != null) {
            uploadAttachedFileList(newPost, postCreationReq.getAttachedFileList());
        }
    }

    private void uploadAttachedFileList(Post post, List<MultipartFile> attachedFileList) {
        List<BaseFile> baseFiles = attachedFileUseCase.uploadPostFileList(attachedFileList);

        baseFiles.forEach(baseFile ->
                attachedFileRepo.save(
                    AttachedFile.createAttachedFile(
                    post,
                    baseFile.getFileUrl(),
                    baseFile.getOriginFileName(),
                    baseFile.getStoredFileName()
                ))
        );
    }

    @Override
    @Transactional
    public PostViewRes viewPost(Long postId) {
        Post post = postQueryableRepo.findPostByPostId(postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_POST));
        post.increaseViews();
        return PostViewRes.of(post, attachedFileUseCase.getPostFileList(postId),
                commentUseCase.getCommentsFromPost(postId));
    }

    @Override
    public List<PostSearchRes> getPostsByUserId(Long userId) {
        List<Post> posts = postQueryableRepo.findPostsByUserId(userId);
        return posts.stream().map(PostSearchRes::of).toList();
    }

    @Override
    public List<PostSearchRes> getAllPosts() {
        List<Post> posts = postQueryableRepo.findAllPosts();
        return posts.stream().map(PostSearchRes::of).toList();
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
        attachedFileUseCase.deletePostFileList(postId);
        commentUseCase.deleteAllByPostId(postId);
        postRepo.delete(post);
    }

    @Override
    public List<PostSearchRes> searchPosts(PostSearchInput postSearchInput) {
        List<Post> posts = postQueryableRepo.findPostsWithSearchOption(postSearchInput);
        return posts.stream().map(PostSearchRes::of).toList();
    }
}
