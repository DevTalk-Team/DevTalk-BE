package com.devtalk.consultation.consultationservice.consultation.application.port.in.dto;


import com.devtalk.consultation.consultationservice.consultation.domain.consultation.AttachedFile;
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
