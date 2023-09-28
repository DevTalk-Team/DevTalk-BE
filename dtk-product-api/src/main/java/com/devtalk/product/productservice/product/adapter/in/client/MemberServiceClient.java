package com.devtalk.product.productservice.product.adapter.in.client;

import com.devtalk.product.productservice.product.application.port.in.dto.MemberReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "member-service")
public interface MemberServiceClient {
    @RequestMapping("/member/consultant/{memberId}/info")
    MemberReq.ConsultantPriceReq getPrice(@PathVariable Long memberId);
}
