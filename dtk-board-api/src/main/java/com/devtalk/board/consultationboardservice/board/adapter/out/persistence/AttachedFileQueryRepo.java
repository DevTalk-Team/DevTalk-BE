package com.devtalk.board.consultationboardservice.board.adapter.out.persistence;

import com.devtalk.board.consultationboardservice.board.application.port.out.repository.AttachedFileQueruableRepo;
import com.devtalk.board.consultationboardservice.board.domain.attachedfile.AttachedFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.devtalk.board.consultationboardservice.board.domain.attachedfile.QAttachedFile.attachedFile;

@Repository
@RequiredArgsConstructor
public class AttachedFileQueryRepo implements AttachedFileQueruableRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<AttachedFile> findByPostId(Long postId) {
        return queryFactory.selectFrom(attachedFile)
                .where(attachedFile.post.id.eq(postId))
                .fetch();
    }
}
