package com.devtalk.board.consultationboardservice.board.application.port.out.repository;

import com.devtalk.board.consultationboardservice.board.domain.attachedfile.AttachedFile;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;

import java.util.List;

public interface AttachedFileQueruableRepo {
    List<AttachedFile> findByPostId(Long postId);
}
