package com.devtalk.consultation.consultationservice.consultation.adapter.out.persistence;


import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.AttachedFileQueruableRepo;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.AttachedFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import static com.devtalk.consultation.consultationservice.consultation.domain.consultation.QAttachedFile.attachedFile;

@Repository
@RequiredArgsConstructor
public class AttachedFileQueryRepo implements AttachedFileQueruableRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<AttachedFile> findByConsultationId(Long consultationId) {
        return queryFactory.selectFrom(attachedFile)
                .where(attachedFile.consultation.id.eq(consultationId))
                .fetch();
    }
}
