package com.devtalk.board.consultationboardservice.board.application.port.out.repository;

import com.devtalk.board.consultationboardservice.board.domain.attachedfile.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachedFileRepo extends JpaRepository<AttachedFile, Long> {
}
