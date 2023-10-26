package com.devtalk.member.memberservice.global.vo;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BaseFile {
    private String fileUrl;
    private String originFileName;
    private String storedFileName;
}
