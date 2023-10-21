package com.devtalk.consultation.consultationservice.consultation.application.port.out.repository;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AttachedFileRepo extends JpaRepository<AttachedFile, Long> {
}
