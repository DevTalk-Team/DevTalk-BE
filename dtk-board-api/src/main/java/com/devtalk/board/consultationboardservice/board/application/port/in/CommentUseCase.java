package com.devtalk.board.consultationboardservice.board.application.port.in;

import com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.CommentInput;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.CommentReq;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.CommentRes;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostRes;

import java.util.List;

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.CommentInput.*;
import static com.devtalk.board.consultationboardservice.board.application.port.in.dto.CommentReq.*;

public interface CommentUseCase {
    void createComment(CommentCreationReq commentCreationReq);

    void modifyComment(CommentModifyReq commentModifyReq);

    void deleteComment(Long userId, Long commentId);

    void deleteAllByPostId(Long postId);
    CommentRes getComment(Long commentId);

    List<CommentRes> getCommentsFromPost(Long postId);
}
