package com.devtalk.consultation.consultationservice.consultation.application.port.in;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.AttachedFileRes;
import com.devtalk.consultation.consultationservice.global.vo.BaseFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadUseCase {
    BaseFile uploadReviewPhoto(MultipartFile multipartFile);
    List<AttachedFileRes> getConsultationFileList(Long postId);
    List<BaseFile> uploadConsultationFileList(List<MultipartFile> multipartFiles);
    List<BaseFile> uploadFileList(List<MultipartFile> multipartFiles, String path);
    BaseFile uploadFile(MultipartFile multipartFile, String path);
    void deleteConsultationFileList(Long postId);
    void deleteFile(String storedFileName, String path);
}
