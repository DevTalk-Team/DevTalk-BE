package com.devtalk.board.consultationboardservice.board.application.validator;

import com.devtalk.board.consultationboardservice.board.domain.comment.Comment;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import com.devtalk.board.consultationboardservice.global.error.ErrorCode;
import com.devtalk.board.consultationboardservice.global.error.exception.IncorrectException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class CommentValidator {
    public void checkUser(Comment comment, Long userId) {
        if (!comment.getUserId().equals(userId)) {
            throw new IncorrectException(ErrorCode.INCORRECT_USER_TRY_TO_MODIFY_OR_DELETE);
        }
    }
}
