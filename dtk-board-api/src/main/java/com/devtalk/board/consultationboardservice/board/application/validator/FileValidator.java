package com.devtalk.board.consultationboardservice.board.application.validator;

import com.devtalk.board.consultationboardservice.global.error.exception.FileException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.devtalk.board.consultationboardservice.global.error.ErrorCode.*;

@Component
@NoArgsConstructor
public class FileValidator {

    @Value("${file.list-max-size}")
    private Integer fileListMaxSize;

    @Value("${file.list-max-count}")
    private Integer fileListMaxCount;

    public FileValidator(
            @Value("${file.list-max-size}") Integer fileListMaxSize,
            @Value("${file.list-max-count}") Integer fileListMaxCount) {
        this.fileListMaxSize = fileListMaxSize;
        this.fileListMaxCount = fileListMaxCount;
    }

    public void checkFileCountAndSizeAndExtension(List<MultipartFile> fileList) {
        if(fileList == null || fileList.isEmpty()) return;
        checkExceedMaxCount(fileList.size());
        checkExceedMaxSize(fileList);
        checkFileExtension(fileList);
    }

    public void checkExceedMaxCount(Integer fileCount) {
        if(fileCount > fileListMaxCount) {
            throw new FileException(EXCESS_FILE_LIST_COUNT);
        }
    }

    public void checkFileExtension(List<MultipartFile> fileList) {
        fileList.forEach(file -> checkFileExtension(file));
    }

    public void checkFileExtension(MultipartFile file) {
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        if (fileExtension.equals("png") || fileExtension.equals("jpg") || fileExtension.equals("jpeg")
                || fileExtension.equals("pdf") || fileExtension.equals("ppt") || fileExtension.equals("xlsx")
                || fileExtension.equals("xls") || fileExtension.equals("doc") || fileExtension.equals("docx")) {
            return;
        }
        throw new FileException(UNSUPPORTED_FILE_EXTENSION);
    }

    // 리스트 내의 파일 사이즈 총합이 허용사이즈 이상인 경우 에러
    public void checkExceedMaxSize(List<MultipartFile> fileList) {
        if(fileList.stream().mapToLong(MultipartFile::getSize).sum() > fileListMaxSize) {
            throw new FileException(EXCESS_FILE_LIST_SIZE);
        }
    }


}
