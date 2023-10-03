package com.devtalk.board.consultationboardservice.board.application.validator;

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

    public void validatePost(PostCreationReq postCreationReq) {
        validateAttachedFileList(postCreationReq.getAttachedFileList());
    }

    public void validateUser(Post post, Long userId) {
        postValidator.checkUser(post, userId);
    }

    private void validateAttachedFileList(List<MultipartFile> attachedFileList) {
        fileValidator.checkFileCountAndSizeAndExtension(attachedFileList);
    }

}
