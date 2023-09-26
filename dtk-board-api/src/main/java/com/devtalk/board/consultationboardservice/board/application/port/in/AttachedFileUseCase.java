package com.devtalk.board.consultationboardservice.board.application.port.in;


import com.devtalk.board.consultationboardservice.board.domain.attachedfile.AttachedFile;
import com.devtalk.board.consultationboardservice.global.vo.BaseFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachedFileUseCase {
    List<BaseFile> uploadFileList(List<MultipartFile> multipartFiles, String path);

    BaseFile uploadFile(MultipartFile multipartFile, String path);
}
