package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultantInput;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantRes;

import java.util.List;

public interface ConsultantInfoUseCase {
    ConsultantRes.InfoRes getInfo(String token);
    ConsultantRes.InfoRes updateInfo(String token, ConsultantInput.InfoInput input);
    List<String> getType(String token);
    void updateType(String token, ConsultantInput.ListInput input);
    List<String> getCategory(String token);
    void updateCategory(String token, ConsultantInput.ListInput input);
    List<String> getRegion(String token);
    void updateRegion(String token, ConsultantInput.ListInput input);
}
