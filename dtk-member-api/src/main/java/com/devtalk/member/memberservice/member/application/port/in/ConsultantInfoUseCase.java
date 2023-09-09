package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultantInput;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultationCategoryInput;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantRes;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantTypeRes;

import java.util.List;

public interface ConsultantInfoUseCase {
    ConsultantRes.InfoRes getInfo(String token);
    ConsultantRes.InfoRes updateInfo(String token, ConsultantInput.InfoInput input);
    List<String> getCategory(String token);
    ConsultantTypeRes updateType(String token, List<String> type);

    void updateCategory(String token, ConsultationCategoryInput category);
}
