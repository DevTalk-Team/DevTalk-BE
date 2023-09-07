package com.devtalk.member.memberservice.member.application.port.out.repository;

import com.devtalk.member.memberservice.member.domain.category.MemberCategory;

import java.util.List;

public interface MemberCategoryQueryableRepo {
    List<MemberCategory> findAllById(Long id);
    void deleteAllById(Long id);
}
