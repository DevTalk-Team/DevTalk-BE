package com.devtalk.board.consultationboardservice.board.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostReq {
    private Long id;
    private String title;
    private String content;
    private String writer;
}
