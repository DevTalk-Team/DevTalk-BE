//package com.devtalk.product.productservice.product.adapter.in.client;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@FeignClient(name = "matching")
//public interface MatchingServiceClient {
//    @GetMapping("/{userId}/teams")
//    MatchingResponseData getTeam(@PathVariable("userId") Long id);
//}