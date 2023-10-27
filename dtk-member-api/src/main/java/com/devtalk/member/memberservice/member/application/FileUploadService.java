package com.devtalk.member.memberservice.member.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.FileException;
import com.devtalk.member.memberservice.global.vo.BaseFile;
import com.devtalk.member.memberservice.member.application.port.in.FileUploadUseCase;
import com.devtalk.member.memberservice.member.application.port.out.dto.ProfileFileRes;
import com.devtalk.member.memberservice.member.application.port.out.repository.ProfileImageQueryableRepo;
import com.devtalk.member.memberservice.member.application.port.out.repository.ProfileImageRepo;
import com.devtalk.member.memberservice.member.domain.consultation.ProfileImage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileUploadService implements FileUploadUseCase {
    private final AmazonS3 amazonS3;
    private final ProfileImageQueryableRepo profileImageQueryableRepo;
    private final ProfileImageRepo profileImageRepo;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${spring.application.name}")
    private String appName;

    @Override
    public List<ProfileFileRes> getConsultantInfoFileList(Long consultantInfoId) {
        List<ProfileImage> profileImageList = profileImageQueryableRepo.findByConsultantInfoId(consultantInfoId);
        List<ProfileFileRes> profileFileResList = new ArrayList<>();
        profileImageList.forEach(profileImage -> {
            profileFileResList.add(ProfileFileRes.of(profileImage));
        });
        return profileFileResList;
    }

    @Override
    public List<BaseFile> uploadConsultantInfoFileList(List<MultipartFile> multipartFiles) {
        return uploadFileList(multipartFiles, appName);
    }

    @Transactional
    @Override
    public List<BaseFile> uploadFileList(List<MultipartFile> multipartFiles, String path) {
        List<BaseFile> fileList = new ArrayList<>();

        multipartFiles.stream().forEach(multipartFile -> {
            fileList.add(uploadFile(multipartFile, path));
        });

        return fileList;
    }

    @Override
    public BaseFile uploadFile(MultipartFile multipartFile, String path) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(originalFilename);
        String storedFileName = UUID.randomUUID() + "." + fileExtension;
        String objectKey = path + "/" + storedFileName;

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucketName, objectKey, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }

        return BaseFile.builder()
                .storedFileName(storedFileName)
                .originFileName(originalFilename)
                .fileUrl(amazonS3.getUrl(bucketName, objectKey).toString())
                .build();
    }

    @Transactional
    @Override
    public void deleteConsultantInfoFileList(Long consultantInfoId) {
        profileImageQueryableRepo.deleteAllByConsultantInfoId(consultantInfoId);
        List<ProfileImage> profileImageList = profileImageQueryableRepo.findByConsultantInfoId(consultantInfoId);
        profileImageList.forEach(profileImage -> {
            deleteFile(profileImage.getStoredFileName(), appName);
            profileImageRepo.delete(profileImage);
        });
    }

    @Transactional
    @Override
    public void deleteFile(String storedFileName, String path) {
        String objectKey = path + "/" + storedFileName;
        if (!amazonS3.doesObjectExist(bucketName, objectKey)) {
            throw new FileException(ErrorCode.FILE_NOT_FOUND);
        }
        amazonS3.deleteObject(bucketName, objectKey);
    }
}
