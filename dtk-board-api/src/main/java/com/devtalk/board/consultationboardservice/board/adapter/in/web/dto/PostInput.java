package com.devtalk.board.consultationboardservice.board.adapter.in.web.dto;

import lombok.Getter;

@Getter
public class PostInput{
    private String title;
    private String content;
    private Long userId; // TODO : input에서 userId를 받아야 하는가, controller에서 받아야 하는가
}