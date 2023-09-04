package com.devtalk.product.productservice.product.application;

import com.devtalk.product.productservice.global.error.exception.NotFoundException;
import com.devtalk.product.productservice.product.adapter.out.web.persistence.MemberQueryRepo;
import com.devtalk.product.productservice.product.application.port.in.SearchUseCase;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductRes;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ReservedProductRepo;
import com.devtalk.product.productservice.product.domain.member.Consulter;
import com.devtalk.product.productservice.product.domain.member.Member;
import com.devtalk.product.productservice.product.domain.member.MemberType;
import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ProductReservedDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.devtalk.product.productservice.global.error.ErrorCode.NOT_FOUND_MEMBER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService implements SearchUseCase {
    ProductRepo productRepo;
    MemberQueryRepo memberQueryRepo;
    ReservedProductRepo reservedProductRepo;
    //상담자 예약 가능 상품 조회
    @Transactional
    public List<ProductRes.ConsultantProductListRes> searchList(Long consultantId) {
        List<Product> productList =  productRepo.findAllByConsultantId(consultantId);

        return productList.stream()
                .map(product -> ProductRes.ConsultantProductListRes.builder()
                        .productId(product.getId())
                        .consultantId(product.getConsultant().getId())
                        .status(product.getStatus())
                        .reservationAt(product.getReservationAt())
                        .productProceedType(product.getType())
                        .build())
                .collect(Collectors.toList());
    }

    //마이페이지 예약 리스트 조회
    @Transactional
    public List<ProductRes.ReservedProductRes> searchConsulationListByMemberId(Long memberId){
        List<ProductReservedDetails> productReservedDetails;
        Optional<Consulter> memberOpt = memberQueryRepo.findById(memberId);
        if (!memberOpt.isPresent()) {
            throw new NotFoundException(NOT_FOUND_MEMBER);
        }

        Member member = memberOpt.get();

        if (member.getMemberType() == MemberType.CONSULTANT) {
            productReservedDetails = reservedProductRepo.findAllByConsultantId(member.getId());
        } else if (member.getMemberType() == MemberType.CONSULTER) {
            productReservedDetails = reservedProductRepo.findAllByConsulterId(member.getId());
        } else {
            throw new RuntimeException("Unknown Member Type");
        }
        return productReservedDetails.stream()
                .map(productReservedDetail -> ProductRes.ReservedProductRes.builder()
                        .productId(productReservedDetail.getProduct().getId())
                        .consultationId(productReservedDetail.getId())
                        .consultantId(productReservedDetail.getProduct().getConsultant().getId())
                        .consulterId(productReservedDetail.getConsulterId())
                        .status("예약 중")
                        .reservationAt(productReservedDetail.getProduct().getReservationAt())
                        .price(productReservedDetail.getPrice())
                        .reservedProceedType(productReservedDetail.getReservedProceedType())
                        .area(productReservedDetail.getRegion())
                        .build())
                .collect(Collectors.toList());
    }
}
