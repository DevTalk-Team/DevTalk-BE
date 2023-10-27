package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.global.vo.BaseFile;
import com.devtalk.member.memberservice.member.application.port.out.dto.ProfileFileRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadUseCase {
    List<ProfileFileRes> getConsultantInfoFileList(Long consultantInfoId);
    List<BaseFile> uploadConsultantInfoFileList(List<MultipartFile> multipartFiles);
    List<BaseFile> uploadFileList(List<MultipartFile> multipartFiles, String path);
    BaseFile uploadFile(MultipartFile multipartFile, String path);
    void deleteConsultantInfoFileList(Long consultantInfoId);
    void deleteFile(String storedFileName, String path);
}
