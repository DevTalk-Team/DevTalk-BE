package com.devtalk.consultation.consultationservice.global.vo;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BaseFile {
    private String fileUrl;

    private String originName;

    private String storedName;
}
