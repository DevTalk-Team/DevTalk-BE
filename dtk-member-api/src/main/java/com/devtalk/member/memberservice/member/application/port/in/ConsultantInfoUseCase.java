package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultantInfoInput;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultationCategoryInput;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantInfoRes;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantTypeRes;

import java.util.List;

public interface ConsultantInfoUseCase {
    ConsultantInfoRes getInfo(String token);
    ConsultantInfoRes updateInfo(String token, ConsultantInfoInput input);
    List<String> getCategory(String token);
    ConsultantTypeRes updateType(String token, List<String> type);

    void updateCategory(String token, ConsultationCategoryInput category);
}
