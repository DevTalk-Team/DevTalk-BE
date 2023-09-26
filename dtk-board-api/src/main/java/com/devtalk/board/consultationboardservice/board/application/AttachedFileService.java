package com.devtalk.board.consultationboardservice.board.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devtalk.board.consultationboardservice.board.application.port.in.AttachedFileUseCase;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.AttachedFileRepo;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostQueryableRepo;
import com.devtalk.board.consultationboardservice.board.domain.attachedfile.AttachedFile;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import com.devtalk.board.consultationboardservice.global.error.ErrorCode;
import com.devtalk.board.consultationboardservice.global.error.exception.FileException;
import com.devtalk.board.consultationboardservice.global.error.exception.NotFoundException;
import com.devtalk.board.consultationboardservice.global.vo.BaseFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachedFileService implements AttachedFileUseCase {
    private final AmazonS3 amazonS3;
    private final PostQueryableRepo postQueryableRepo;
    private final AttachedFileRepo attachedFileRepo;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${spring.application.name}")
    private String appName;

    @Override
    @Transactional
    public List<BaseFile> uploadFileList(List<MultipartFile> multipartFiles, String path) {
        /*
        * 1. 이 파일이 어느 post의 파일들인지 알아야함
        * 2. 원래 파일명 저장
        * 3. AWS S3에 저장될 파일명 저장 (UUID로 저장)
        * 4. 파일의 URL 주소 저장
        * 5. 파일 객체 저장
        * */
        List<BaseFile> fileList = new ArrayList<>();

        multipartFiles.stream().forEach(multipartFile -> {
            fileList.add(uploadFile(multipartFile, path));
        });

        return fileList;
    }



    @Override
    public BaseFile uploadFile(MultipartFile multipartFile, String path) {
        if(multipartFile.isEmpty()) return null;

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

    private String getFileExtension(String originFileName) {
        try {
            return originFileName.substring(originFileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            // TODO : validation check 하자
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + originFileName + ") 입니다.");
        }

    }
}
