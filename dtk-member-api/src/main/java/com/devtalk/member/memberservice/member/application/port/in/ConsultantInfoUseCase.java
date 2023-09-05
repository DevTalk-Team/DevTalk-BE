package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantCategoryDto;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantInfoRes;

public interface ConsultantInfoUseCase {
    ConsultantInfoRes getInfo(String token);
    ConsultantCategoryDto getCategory();
}
