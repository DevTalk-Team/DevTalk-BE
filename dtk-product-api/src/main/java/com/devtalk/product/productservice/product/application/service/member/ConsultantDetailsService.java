//package com.devtalk.product.productservice.product.application.service.member;
//
//import com.devtalk.product.productservice.global.error.exception.NotFoundException;
//import com.devtalk.product.productservice.product.application.port.in.dto.ConsultantPrivacyReq;
//import com.devtalk.product.productservice.product.application.port.in.member.ConsultantDetailsUseCase;
//import com.devtalk.product.productservice.product.application.port.out.repository.MemberQueryableRepo;
//import com.devtalk.product.productservice.product.application.port.out.repository.MemberRepo;
//import com.devtalk.product.productservice.product.domain.member.Consultant;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import static com.devtalk.product.productservice.global.error.ErrorCode.NOT_FOUND_CONSULTANT;
//
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class ConsultantDetailsService implements ConsultantDetailsUseCase {
//    private final MemberRepo memberRepo;
//    private final MemberQueryableRepo memberQueryableRepo;
//    @Transactional
//    public void privacyUpdate(ConsultantPrivacyReq consultantPrivacyReq) {
//
//        Consultant optionalConsultant = memberQueryableRepo.findByConsultantId(consultantPrivacyReq.getId())
//                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTANT));
//        optionalConsultant.updatePrivacy(consultantPrivacyReq);
//        memberRepo.save(optionalConsultant);
//    }
//}
