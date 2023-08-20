package com.devtalk.member.memberservice.member.adapter.out.persistence;

import com.devtalk.member.memberservice.member.application.port.out.repository.MemberQueryableRepo;
import org.springframework.stereotype.Repository;

@Repository
public class MemberQueryRepo implements MemberQueryableRepo {

//    JPAQueryFactory queryFactory;

    @Override
    public String findEmailByNameAndPhoneNumber(String name, String phoneNumber) {
        return null;
    }
}
