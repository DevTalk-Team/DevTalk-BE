package com.devtalk.consultation.consultationservice.consultation.application.port.out.repository;

import com.devtalk.consultation.consultationservice.consultation.domain.member.Consultant;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Consulter;

public interface MemberCommandableRepo {
    void save(Consulter consulter);

    void save(Consultant consultant);
}
