package com.devtalk.board.consultationboardservice.board.adapter.in.web.dto;

import lombok.Getter;

public class BoardInput {
    @Getter
    public static class PostInput{
        private String title;
        private String content;
        private Long writerId;
    }
}
