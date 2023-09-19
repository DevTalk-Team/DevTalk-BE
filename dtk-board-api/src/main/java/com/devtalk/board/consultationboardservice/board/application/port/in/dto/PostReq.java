package com.devtalk.board.consultationboardservice.board.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostReq {
    private String title;
    private String content;
    private Long userId;
}
