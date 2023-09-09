package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultantInput;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultationCategoryInput;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantRes;

import java.util.List;

public interface ConsultantInfoUseCase {
    ConsultantRes.InfoRes getInfo(String token);
    ConsultantRes.InfoRes updateInfo(String token, ConsultantInput.InfoInput input);
    ConsultantRes.TypeRes updateType(String token, ConsultantInput.TypeInput input);
    List<String> getCategory(String token);
    void updateCategory(String token, ConsultationCategoryInput category);
}
