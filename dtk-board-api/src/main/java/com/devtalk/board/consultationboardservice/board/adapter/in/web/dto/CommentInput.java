package com.devtalk.board.consultationboardservice.board.adapter.in.web.dto;

import lombok.Getter;

@Getter
public class CommentInput{
    private Long userId;
    private String content;
}
