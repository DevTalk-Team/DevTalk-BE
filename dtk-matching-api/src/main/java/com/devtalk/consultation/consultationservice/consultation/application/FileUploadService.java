package com.devtalk.consultation.consultationservice.consultation.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.FileUploadUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.AttachedFileRes;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.AttachedFileQueruableRepo;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.AttachedFileRepo;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.AttachedFile;
import com.devtalk.consultation.consultationservice.global.error.ErrorCode;
import com.devtalk.consultation.consultationservice.global.error.execption.FileException;
import com.devtalk.consultation.consultationservice.global.vo.BaseFile;
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
@RequiredArgsConstructor
public class FileUploadService implements FileUploadUseCase {
    private final AmazonS3 amazonS3;
    private final AttachedFileQueruableRepo attachedFileQueruableRepo;
    private final AttachedFileRepo attachedFileRepo;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${spring.application.name}")
    private String appName;

//    @Value("${file.consultations}")
//    private String consultationFolderPath;

    @Value("${file.reviews}")
    private String reviewFolderPath;



    @Override
    public BaseFile uploadReviewPhoto(MultipartFile multipartFile) throws FileException {
        return uploadFile(multipartFile, reviewFolderPath);
    }



    @Override
    public List<AttachedFileRes> getConsultationFileList(Long consultationId) {
        List<AttachedFile> attachedFileList = attachedFileQueruableRepo.findByConsultationId(consultationId);
        List<AttachedFileRes> attachedFileResList = new ArrayList<>();
        attachedFileList.forEach(attachedFile -> {
            attachedFileResList.add(AttachedFileRes.of(attachedFile));
        });
        return attachedFileResList;
    }
    @Override
    public List<BaseFile> uploadConsultationFileList(List<MultipartFile> multipartFiles) {
        return uploadFileList(multipartFiles, appName);
    }

    @Override
    @Transactional
    public List<BaseFile> uploadFileList(List<MultipartFile> multipartFiles, String path) {
        List<BaseFile> fileList = new ArrayList<>();

        multipartFiles.stream().forEach(multipartFile -> {
            fileList.add(uploadFile(multipartFile, path));
        });

        return fileList;
    }

    @Override
    public BaseFile uploadFile(MultipartFile multipartFile, String path) {
        String originFileName = multipartFile.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(originFileName);
        String storedFileName = UUID.randomUUID() + "." + fileExtension;
        String objectKey = path + "/" + storedFileName;

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucketName, objectKey, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }

        return BaseFile.builder()
                .storedFileName(storedFileName)
                .originFileName(originFileName)
                .fileUrl(amazonS3.getUrl(bucketName, objectKey).toString())
                .build();
    }
    @Override
    public void deleteConsultationFileList(Long consultationId) {
        List<AttachedFile> attachedFileList = attachedFileQueruableRepo.findByConsultationId(consultationId);
        attachedFileList.forEach(attachedFile -> {
            deleteFile(attachedFile.getStoredFileName(), appName);
            attachedFileRepo.delete(attachedFile);
        });
    }

    @Override
    public void deleteFile(String storedFileName, String path) {
        String objectKey = path + "/" + storedFileName;
        if(!amazonS3.doesObjectExist(bucketName, objectKey)){
            throw new FileException(ErrorCode.NOT_FOUND_FILE);
        }
        amazonS3.deleteObject(bucketName, objectKey);
    }

}
