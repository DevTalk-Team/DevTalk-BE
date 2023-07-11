package com.devtalk.member.memberservice.member.application.port.out.repository;

import com.devtalk.member.memberservice.member.adapter.out.persistence.CounseleeJpaEntity;
import com.devtalk.member.memberservice.member.domain.Counselee;

/* DB랑 이어지는 포트 interface */
public interface CounseleeCommandableRepo {

    public void save(Counselee domain);
}