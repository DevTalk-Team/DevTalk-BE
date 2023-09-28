package com.devtalk.board.consultationboardservice.board.application.validator;

import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostReq.*;

@Service
@RequiredArgsConstructor
public class BoardValidator {
    private final FileValidator fileValidator;

    public void validatePost(PostCreationReq postCreationReq) {
        validateAttachedFileList(postCreationReq.getAttachedFileList());
    }

    private void validateAttachedFileList(List<MultipartFile> attachedFileList) {
        fileValidator.checkFileCountAndSizeAndExtension(attachedFileList);
    }
}
