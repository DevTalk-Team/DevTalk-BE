package com.devtalk.board.consultationboardservice.board.application.port.in.dto;

import com.devtalk.board.consultationboardservice.board.domain.attachedfile.AttachedFile;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AttachedFileReq {
    private Post post;
    private String fileUrl;
    private String originFileName;
    private String storedFileName;

    public AttachedFile toEntity(Post post) {
        return AttachedFile.createAttachedFile(post, fileUrl, originFileName, storedFileName);
    }
}
