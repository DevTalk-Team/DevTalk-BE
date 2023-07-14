package com.devtalk.consultation.consultationservice.global.util;

import com.devtalk.consultation.consultationservice.global.error.ErrorCode;
import com.devtalk.consultation.consultationservice.global.error.execption.FileException;
import com.devtalk.consultation.consultationservice.global.vo.BaseFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileUploadService {

    @Value("${file.inbound}")
    private String inbound;

    @Value("${file.outbound}")
    private String outbound;

    @Value("${file.consultations}")
    private String consultationFolderPath;

    public List<BaseFile> uploadFileList(List<MultipartFile> multipartFileList) throws FileException {
        List<BaseFile> fileList = new ArrayList<>();

        multipartFileList.stream().forEach(multipartFile -> {
            fileList.add(uploadFile(multipartFile, consultationFolderPath));
        });

        return fileList;
    }

    private BaseFile uploadFile(MultipartFile multipartFile, String path) {
        if(multipartFile == null) {
            return null;
        }
        try {
            String fileOriName = multipartFile.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(fileOriName);

            String fileUrl = path + "/" + LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getDayOfMonth() + "/";

            String destinationFileName = UUID.randomUUID() + "." + fileExtension;
            File destinationFile = new File(outbound + fileUrl + destinationFileName);
            destinationFile.getParentFile().mkdirs();
            multipartFile.transferTo(destinationFile);

            return BaseFile.builder()
                    .storedName(destinationFileName)
                    .originName(fileOriName)
                    .fileUrl(inbound + fileUrl)
                    .build();

        } catch (Exception e) {
            throw new FileException(ErrorCode.SERVER_ERROR);
        }
    }


}
