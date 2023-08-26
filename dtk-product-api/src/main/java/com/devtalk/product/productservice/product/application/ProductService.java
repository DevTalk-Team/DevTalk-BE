package com.devtalk.product.productservice.product.application;

import com.devtalk.product.productservice.product.application.port.in.ProductUseCase;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.RegistProdReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.ReserveProdReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductRes;
import com.devtalk.product.productservice.product.application.port.out.repository.MemberQueryableRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.MemberRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ReservedProductRepo;
import com.devtalk.product.productservice.product.application.validator.Validator;
import com.devtalk.product.productservice.global.error.exception.NotFoundException;
import static com.devtalk.product.productservice.global.error.ErrorCode.*;


import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.member.Consulter;
import com.devtalk.product.productservice.product.domain.member.Member;
import com.devtalk.product.productservice.product.domain.product.Product;

import com.devtalk.product.productservice.product.domain.product.ReservedDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {
    private final Validator validator;
    private final MemberQueryableRepo memberQueryableRepo;
    private final ProductRepo productRepo;
    private final MemberRepo memberRepo;
    private final ReservedProductRepo reservedProductRepo;
    private final ProductRes.ReservedProductRes reservedProductRes ;

    //상품등록
    @Transactional
    public void registProduct(RegistProdReq registProdReq) {
        /*
        검증이 필요한가?
         */
        //validator.validate(registProdReq);
        Consultant consultant = searchConsultant(registProdReq.getConsultantId());
        Product product = Product.registProduct(consultant, registProdReq.getReservationAt(),
                registProdReq.getType());
        productRepo.save(product);
    }

    //전문가별 예약가능리스트 조회
    @Transactional
    public List<Product> searchList(Long consultantId) {
        /*
        여기선 필요할 수 있을거 같긴한데 해당 consultantId가 없습니다. 근데 없을 순 없을거 같음
        이미 프론트에서 정해진 회원의 정보를 선택해서 가져오는거니까
         */
        //validator.validate(consultantId);
        return productRepo.findAllByConsultantId(consultantId);
    }

    //상품 예약
    @Transactional
    public void reserveProduct(ReserveProdReq reserveProdReq) {
        Product product = searchProduct(reserveProdReq.getProductId());
        Consultant consultant = searchConsultant(product.getId());
        Consulter consulter = searchConsulter(reserveProdReq.getConsulterId());
        ReservedDetails reservedDetails = ReservedDetails.reserveProduct(product, consultant, consulter,
                reserveProdReq.getReservedType());
        reservedProductRepo.save(reservedDetails);
    }

    //예약 취소


    //예약상품조회
    @Transactional
    public ProductRes.ReservedProductRes searchReservedDetatils(Long consultationId) {
        ReservedDetails reservedDetails = reservedProductRepo.findById(consultationId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_RESERVED_DETAILS));

        ProductRes.ReservedProductRes reservedProductInfo = ProductRes.loadInfo(reservedDetails);
        return reservedProductInfo;
    }

    //회원별 예약 리스트 조회
    @Transactional
    public List<ProductRes.ReservedProductRes> searchReservedProductsByMember(Long memberId) {
        List<ReservedDetails> reservedDetailsList = reservedProductRepo.findAllByMemberId(memberId);
        List<ProductRes.ReservedProductRes> reservedProductInfos = new ArrayList<>();
        for (ReservedDetails reservedDetails : reservedDetailsList) {
            ProductRes.ReservedProductRes reservedProductInfo = ProductRes.loadInfo(reservedDetails);
            reservedProductInfos.add(reservedProductInfo);
        }

        return reservedProductInfos;
    }

    @Override
    public void deleteReservation(Long consultationId) {
        ProductRes.ReservedProductRes reservedDetails = searchReservedDetatils(consultationId);
        Product product = searchProduct(reservedDetails.getProductId());
        reservedProductRepo.deleteById(reservedDetails.getConsultationId());
        product.deleteReservation(product.getId());
        productRepo.save(product);
        return;
    }

    @Transactional
    public Product searchProduct(Long productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTANT));
    }



    @Transactional
    public Consulter searchConsulter(Long consulterId) {
        Optional<Member> optionalMember = memberRepo.findByconsulterId(consulterId);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member instanceof Consulter) {
                return (Consulter) member;
            } else {
                throw new IllegalArgumentException("Member with given ID is not a Consulter");
            }
        } else {
            throw new NotFoundException(NOT_FOUND_CONSULTER); // or handle the empty case
        }
    }
    @Transactional
    public Consultant searchConsultant(Long consultantId) {
        Optional<Member> optionalMember = memberRepo.findByconsultantId(consultantId);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member instanceof Consultant) {
                return (Consultant) member;
            } else {
                throw new IllegalArgumentException("Member with given ID is not a Consultant");
            }
        } else {
            throw new NotFoundException(NOT_FOUND_CONSULTANT); // or handle the empty case
        }
    }
}
