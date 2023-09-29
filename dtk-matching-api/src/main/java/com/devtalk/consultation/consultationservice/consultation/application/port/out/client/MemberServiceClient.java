package com.devtalk.consultation.consultationservice.consultation.application.port.out.client;

import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto.MemberRes;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto.ProductRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service") // Discovery Server에 등록된 서비스 이름

public interface MemberServiceClient {
    @GetMapping("/member/profile/{consultant}")
    MemberRes.ConsultantRes getConsultantInfo(@PathVariable Long member_id);

    @GetMapping("/member/profile/{consulter}")
    MemberRes.ConsulterRes getConsulterInfo(@PathVariable Long member_id);
}
