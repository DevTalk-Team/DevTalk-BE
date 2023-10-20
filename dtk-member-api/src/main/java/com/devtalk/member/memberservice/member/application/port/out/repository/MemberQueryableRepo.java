package com.devtalk.member.memberservice.member.application.port.out.repository;

import com.devtalk.member.memberservice.member.adapter.out.web.dto.FindProfileOutput;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantReq;
import com.devtalk.member.memberservice.member.application.port.out.dto.ConsultantRes;

import java.util.List;

public interface MemberQueryableRepo {
    String findEmailByNameAndPhoneNumber(String name, String phoneNumber);
    FindProfileOutput.MemberOutput findNameAndEmailById(Long id);
    List<ConsultantRes.ConsultationRes> findNf2fConsultant(Long typeId, Long categoryId);
    List<ConsultantRes.ConsultationRes> findF2fConsultant(Long typeId, Long categoryId, Long regionId);
}
