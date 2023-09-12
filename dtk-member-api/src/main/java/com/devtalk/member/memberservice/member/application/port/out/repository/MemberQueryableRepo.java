package com.devtalk.member.memberservice.member.application.port.out.repository;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.FindProfileOutput;

public interface MemberQueryableRepo {
    String findEmailByNameAndPhoneNumber(String name, String phoneNumber);
    FindProfileOutput.MemberOutput findNameAndEmailById(Long id);
}
