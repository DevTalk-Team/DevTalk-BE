package com.devtalk.board.consultationboardservice.board.application.port.in.dto;

import com.devtalk.board.consultationboardservice.board.domain.attachedfile.AttachedFile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AttachedFileRes {
    private String fileUrl;
    private String originFileName;

    public static AttachedFileRes of(AttachedFile attachedFile) {
        return AttachedFileRes.builder()
                .fileUrl(attachedFile.getFileUrl())
                .originFileName(attachedFile.getOriginFileName())
                .build();
    }
}
