package com.devtalk.consultation.consultationservice.consultation.application.port.out.repository;


import com.devtalk.consultation.consultationservice.consultation.domain.consultation.AttachedFile;

import java.util.List;

public interface AttachedFileQueruableRepo {
    List<AttachedFile> findByPostId(Long postId);
}
