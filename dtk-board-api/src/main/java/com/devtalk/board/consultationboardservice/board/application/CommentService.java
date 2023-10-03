package com.devtalk.board.consultationboardservice.board.application;

import com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.CommentInput;
import com.devtalk.board.consultationboardservice.board.application.port.in.CommentUseCase;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.CommentRes;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.CommentQueryableRepo;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.CommentRepo;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostQueryableRepo;
import com.devtalk.board.consultationboardservice.board.application.validator.BoardValidator;
import com.devtalk.board.consultationboardservice.board.domain.comment.Comment;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import com.devtalk.board.consultationboardservice.global.error.ErrorCode;
import com.devtalk.board.consultationboardservice.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devtalk.board.consultationboardservice.board.application.port.in.dto.CommentReq.*;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentUseCase {
    private final CommentRepo commentRepo;
    private final CommentQueryableRepo commentQueryableRepo;
    private final PostQueryableRepo postQueryableRepo;
    private final MemberService memberService;
    private final BoardValidator boardValidator;

    @Override
    @Transactional
    public void createComment(CommentCreationReq commentCreationReq) {
        Post post = postQueryableRepo.findPostByPostId(commentCreationReq.getPostId())
                .orElseThrow(()->new NotFoundException(ErrorCode.NOT_FOUND_POST));
        String userName = memberService.findUser(commentCreationReq.getUserId()).getName();

        Comment newComment = commentCreationReq.toEntity(post, userName);
        commentRepo.save(newComment);
        post.increaseCommentCount();
    }

    @Override
    public CommentRes getComment(Long commentId) {
        Comment comment = commentQueryableRepo.findByCommentId(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_COMMENT));
        return CommentRes.of(comment);
    }

    @Override
    public List<CommentRes> getCommentsFromPost(Long postId) {
        List<Comment> comments = commentQueryableRepo.findCommentsByPostId(postId);

        return comments.stream().map(CommentRes::of).toList();
    }

    @Override
    @Transactional
    public void modifyComment(CommentModifyReq commentModifyReq) {
        Comment comment = commentQueryableRepo.findByCommentId(commentModifyReq.getCommentId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_COMMENT));
        boardValidator.validateUserComment(comment, commentModifyReq.getUserId());

        comment.modify(commentModifyReq.getContent());
    }

    @Override
    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        Comment comment = commentQueryableRepo.findByCommentId(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_COMMENT));

        boardValidator.validateUserComment(comment, userId);

        comment.getPostId().decreaseCommentCount();
        commentRepo.delete(comment);
    }

    @Override
    public void deleteAllByPostId(Long postId) {
        List<Comment> comments = commentQueryableRepo.findCommentsByPostId(postId);
        commentRepo.deleteAll(comments);
    }
}
