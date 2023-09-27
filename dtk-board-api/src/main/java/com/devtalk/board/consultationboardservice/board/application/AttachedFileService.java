package com.devtalk.board.consultationboardservice.board.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devtalk.board.consultationboardservice.board.application.port.in.AttachedFileUseCase;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.AttachedFileQueruableRepo;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.AttachedFileRepo;
import com.devtalk.board.consultationboardservice.board.domain.attachedfile.AttachedFile;
import com.devtalk.board.consultationboardservice.global.error.ErrorCode;
import com.devtalk.board.consultationboardservice.global.error.exception.FileException;
import com.devtalk.board.consultationboardservice.global.vo.BaseFile;
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
public class AttachedFileService implements AttachedFileUseCase {
    private final AmazonS3 amazonS3;
    private final AttachedFileQueruableRepo attachedFileQueruableRepo;
    private final AttachedFileRepo attachedFileRepo;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public List<BaseFile> uploadPostFileList(List<MultipartFile> multipartFiles) {
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
    public void deletePostFileList(Long postId) {
        List<AttachedFile> attachedFileList = attachedFileQueruableRepo.findByPostId(postId);
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
