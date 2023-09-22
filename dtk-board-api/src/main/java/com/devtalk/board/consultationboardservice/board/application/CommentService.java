package com.devtalk.board.consultationboardservice.board.application;

import com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.CommentInput;
import com.devtalk.board.consultationboardservice.board.application.port.in.CommentUseCase;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.CommentRes;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostRes;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.CommentQueryableRepo;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.CommentRepo;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostQueryableRepo;
import com.devtalk.board.consultationboardservice.board.domain.comment.Comment;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import com.devtalk.board.consultationboardservice.global.error.ErrorCode;
import com.devtalk.board.consultationboardservice.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentUseCase {
    private final CommentRepo commentRepo;
    private final CommentQueryableRepo commentQueryableRepo;
    private final PostQueryableRepo postQueryableRepo;

    @Override
    public void createComment(Long postId, CommentInput commentInput) {
        Post post = postQueryableRepo.findPostByPostId(postId)
                .orElseThrow(()->new NotFoundException(ErrorCode.NOT_FOUND_POST));

        Comment comment = Comment.builder()
                .postId(post)
                .userId(commentInput.getUserId())
                .content(commentInput.getContent())
                .build();

        commentRepo.save(comment);
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
}
