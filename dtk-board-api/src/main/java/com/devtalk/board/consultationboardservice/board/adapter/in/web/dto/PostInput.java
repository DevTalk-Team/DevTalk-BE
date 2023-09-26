package com.devtalk.board.consultationboardservice.board.adapter.in.web.dto;

import com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.devtalk.board.consultationboardservice.board.application.port.in.dto.PostReq.*;

public class PostInput {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostCreationInput{
        @NotBlank @Size(max = 100)
        private String title;

        @NotBlank @Size(max = 1000)
        private String content;

        @Builder.Default
        private List<MultipartFile> attachedFileList = new ArrayList<>();

        public PostCreationReq toReq(Long userId) {
            return PostCreationReq.builder()
                    .userId(userId)
                    .title(title)
                    .content(content)
                    .attachedFileList(attachedFileList)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class PostSearchInput{
        private String title;
        private String content;
        private Long userId; // TODO : input에서 userId를 받아야 하는가, controller에서 받아야 하는가

    }
}