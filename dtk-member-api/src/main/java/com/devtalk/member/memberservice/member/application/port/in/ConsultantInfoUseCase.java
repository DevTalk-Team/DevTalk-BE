package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultantInput;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantReq;
import com.devtalk.member.memberservice.member.application.port.out.dto.ConsultantRes;

import java.util.List;

public interface ConsultantInfoUseCase {
    ConsultantRes.InfoRes getInfo(String email);
    ConsultantRes.InfoRes updateInfo(String email, ConsultantReq.InfoReq req);
    List<String> getType(String email);
    void updateType(String email, ConsultantInput.ListInput input);
    List<String> getCategory(String email);
    void updateCategory(String email, ConsultantInput.ListInput input);
    List<String> getRegion(String email);
    void updateRegion(String email, ConsultantInput.ListInput input);
    List<ConsultantRes.ConsultationRes> findConsultantForConsultation(ConsultantReq.ConsultationReq req);
}
