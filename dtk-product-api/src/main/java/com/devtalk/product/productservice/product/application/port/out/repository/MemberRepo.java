//package com.devtalk.product.productservice.product.application.port.out.repository;
//
//import com.devtalk.product.productservice.product.domain.member.Consultant;
//import com.devtalk.product.productservice.product.domain.member.Member;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//
//public interface MemberRepo extends JpaRepository<Member, Long>, MemberQueryableRepo {
//    @Override
//    Optional<Member> findByconsultantId(Long consultantId);
//    @Override
//    Optional<Member> findByconsulterId(Long consulterId);
//}