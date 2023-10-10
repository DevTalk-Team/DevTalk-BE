package com.devtalk.consultation.consultationservice.consultation.application.port.out.client;

import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto.MemberReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service") // Discovery Server에 등록된 서비스 이름

public interface MemberServiceClient {
    @GetMapping("/member/profile/consultant/{consultant}")
    MemberReq.ConsultantReq getConsultantInfo(@PathVariable Long consultant);

    @GetMapping("/member/profile/consulter/{consulter}")
    MemberReq.ConsulterReq getConsulterInfo(@PathVariable Long consulter);

    @GetMapping("/member/profile/email/{email}")
    MemberReq.ProfileReq getMemberByEmail(@PathVariable String email);
}
