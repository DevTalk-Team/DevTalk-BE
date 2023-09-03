package com.devtalk.product.productservice.product.application;

import com.devtalk.product.productservice.product.application.port.in.ProductUseCase;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.RegistProdReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.ReserveProdReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductRes;
import com.devtalk.product.productservice.product.application.port.out.repository.*;
import com.devtalk.product.productservice.product.application.validator.Validator;
import com.devtalk.product.productservice.global.error.exception.NotFoundException;
import static com.devtalk.product.productservice.global.error.ErrorCode.*;


import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.member.Consulter;
import com.devtalk.product.productservice.product.domain.member.Member;
import com.devtalk.product.productservice.product.domain.product.Product;

import com.devtalk.product.productservice.product.domain.product.ProductReservedDetails;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {
    private final Validator validator;
    //private final MemberQueryableRepo memberQueryableRepo;
    private final ProductRepo productRepo;
    //private final MemberRepo memberRepo;
    private final ConsultantRepo consultantRepo;
    private final ConsulterRepo consulterRepo;
    private final ReservedProductRepo reservedProductRepo;
    //private final ProductRes.ReservedProductRes reservedProductRes ;

    //상품등록
    @Transactional
    public void registProduct(RegistProdReq registProdReq) {
        //todo 검증절차 필요한지 확인하기
        Consultant consultant = searchConsultant(registProdReq.getConsultantId());
        Product product = Product.registProduct(consultant,
                registProdReq.getReservationAt(),
                registProdReq.getType());
        productRepo.save(product);
    }

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

    //상품 수정
    @Transactional
    public void updateProductType(ProductReq.UpdateProdReq updateProdReq){
        Product updateProduct = productRepo.findById(updateProdReq.getProductId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PRODUCT));
        updateProduct.updateProductType(updateProduct);
        productRepo.save(updateProduct);
        return;
    }

    //예약 상품 삭제
    @Transactional
    public void deleteReservation(Long consultationId) {
        ProductRes.ReservedProductRes reservedDetails = searchReservedDetatils(consultationId);
        Product product = searchProduct(reservedDetails.getProductId());
        reservedProductRepo.deleteById(reservedDetails.getConsultationId());
        product.deleteReservation(product.getId());
        productRepo.save(product);
        return;
    }




    //마이페이지 예약 리스트 조회
    @Transactional
    public List<ProductRes.ReservedProductRes> searchConsulationListByMemberId(Long memberId){
        List<ProductReservedDetails> productReservedDetails;
        Optional<Consultant> consultantOpt = consultantRepo.findById(memberId);
        if (consultantOpt.isPresent()) {
            productReservedDetails = reservedProductRepo.findAllByConsultantId(memberId);
        } else {
            Consulter consulter = consulterRepo.findById(memberId)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_MEMBER));
            productReservedDetails = reservedProductRepo.findAllByConsulterId(consulter.getId());
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
                        .area(productReservedDetail.getArea())
                        .build())
                .collect(Collectors.toList());
    }


    //DB에서 예약 세부사항 조회
    @Transactional
    public ProductRes.ReservedProductRes searchReservedDetatils(Long reservationId) {
        ProductReservedDetails productReservedDetails = reservedProductRepo.findById(reservationId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_RESERVED_DETAILS));

        ProductRes.ReservedProductRes reservedProductInfo = ProductRes.loadInfo(productReservedDetails);
        return reservedProductInfo;
    }

    //DB에서 상품 조회
    @Transactional
    public Product searchProduct(Long productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTANT));
    }



































//
//    //상품 예약
//    @Transactional
//    public void reserveProduct(ReserveProdReq reserveProdReq) {
//        Product product = searchProduct(reserveProdReq.getProductId());
//        Consultant consultant = searchConsultant(product.getId());
//        Consulter consulter = searchConsulter(reserveProdReq.getConsulterId());
//        ProductReservedDetails productReservedDetails =
//                ProductReservedDetails.reserveProduct(product, consultant, consulter,
//                reserveProdReq.getReservedProceedType());
//        reservedProductRepo.save(productReservedDetails);
//    }
//
//
//
//
//
//
//    //회원별 예약 리스트 조회
//    @Transactional
//    public List<ProductRes.ReservedProductRes> searchReservedProductsByMember(Long memberId) {
//        List<ProductReservedDetails> productReservedDetailsList = reservedProductRepo.findAllByMemberId(memberId);
//        List<ProductRes.ReservedProductRes> reservedProductInfos = new ArrayList<>();
//        for (ProductReservedDetails productReservedDetails : productReservedDetailsList) {
//            ProductRes.ReservedProductRes reservedProductInfo = ProductRes.loadInfo(productReservedDetails);
//            reservedProductInfos.add(reservedProductInfo);
//        }
//
//        return reservedProductInfos;
//    }






    @Transactional
    public Consulter searchConsulter(Long consulterId) {
        return consulterRepo.findById(consulterId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTER));
    }
    @Transactional
    public Consultant searchConsultant(Long consultantId) {
        return consultantRepo.findById(consultantId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTANT));
    }
}
