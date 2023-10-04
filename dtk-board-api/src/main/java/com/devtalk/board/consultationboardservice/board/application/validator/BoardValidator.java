package com.devtalk.board.consultationboardservice.board.application.validator;

import com.devtalk.board.consultationboardservice.board.domain.comment.Comment;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostReq.*;

@Service
@RequiredArgsConstructor
public class BoardValidator {
    private final FileValidator fileValidator;
    private final PostValidator postValidator;
    private final CommentValidator commentValidator;

    public void validatePost(PostCreationReq postCreationReq) {
        validateAttachedFileList(postCreationReq.getAttachedFileList());
    }

    public void validateUserPost(Post post, Long userId) {
        postValidator.checkUser(post, userId);
    }

    public void validateUserComment(Comment comment, Long userId) {
        commentValidator.checkUser(comment, userId);
    }

    private void validateAttachedFileList(List<MultipartFile> attachedFileList) {
        fileValidator.checkFileCountAndSizeAndExtension(attachedFileList);
    }

}
