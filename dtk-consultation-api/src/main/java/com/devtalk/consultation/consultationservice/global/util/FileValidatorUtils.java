package com.devtalk.consultation.consultationservice.global.util;

import com.devtalk.consultation.consultationservice.global.error.execption.FileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.devtalk.consultation.consultationservice.global.error.ErrorCode.*;

@Component
@Slf4j
public class FileValidatorUtils {

    @Value("${file.list-max-size}")
    static private Integer fileListMaxSize;

    @Value("${file.list-max-count}")
    static private Integer fileListMaxCount;

    public static void checkExceedMaxCount(Integer fileCount) {
        if(fileCount > fileListMaxCount) {
            throw new FileException(EXCESS_FILE_LIST_COUNT);
        }
    }

    public static void checkFileExtension(List<MultipartFile> fileList) {
        fileList.forEach(FileValidatorUtils::checkFileExtension);
    }

    public static void checkFileExtension(MultipartFile file) {
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        if (!fileExtension.equals("png") || !fileExtension.equals("jpg") || !fileExtension.equals("jpeg")
                || !fileExtension.equals("pdf") || !fileExtension.equals("ppt") || !fileExtension.equals("xlsx")
                || !fileExtension.equals("xls") || !fileExtension.equals("doc") || !fileExtension.equals("docx")) {
            throw new FileException(UNSUPPORTED_FILE_EXTENSION);
        }
    }

    // 리스트 내의 파일 사이즈 총합이 허용사이즈 이상인 경우 에러
    public static void checkExceedMaxSize(List<MultipartFile> fileList) {
        if(fileList.stream().mapToLong(MultipartFile::getSize).sum() > fileListMaxSize) {
            throw new FileException(EXCESS_FILE_LIST_SIZE);
        }
    }


}
