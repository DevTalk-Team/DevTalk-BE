package com.devtalk.member.memberservice.member.application.port.out.repository;

public interface MemberQueryableRepo {
    String findEmailByNameAndPhoneNumber(String name, String phoneNumber);
}
