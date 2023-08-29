package com.devtalk.product.productservice.unittest;

import com.devtalk.product.productservice.product.application.ProductService;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.RegistProdReq;

import com.devtalk.product.productservice.product.application.port.out.repository.ConsultantQueryableRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.application.validator.Validator;
import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.devtalk.product.productservice.product.domain.product.ProductProceedType.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class RegistServiceUnitTest {

    @InjectMocks
    ProductService productService;

    @Mock
    Validator validator;
    @Mock
    ProductRepo productRepo;
    @Mock
    ConsultantQueryableRepo consultantQueryableRepo;

    @Test
    @DisplayName("s. 예약성공")
     void 상품등록성공(){
        System.out.printf("등록시작");
        // given
        Consultant consultant = getConsultant();
        Product product = getProduct(consultant);

        RegistProdReq registProdReq = getRegistProdReq(consultant.getId());

        willDoNothing().given(validator).validate(registProdReq);
        given(consultantQueryableRepo.findByConsultantId(consultant.getId())).willReturn(Optional.of(consultant));
        given(productRepo.save(any())).willReturn(product);

        //when
        productService.registProduct(registProdReq);

        //then
        then(productRepo).should(times(1)).save(any());
    }

    private RegistProdReq getRegistProdReq(Long consultantId) {
        return RegistProdReq.builder()
                .consultantId(consultantId)
                .reservationAt(LocalDateTime.now().plusDays(1))
                .type(NF2F)
                .status("등록 완료")
                .area("서울시 강남")
                .price(0)
                .build();
    }

    private Consultant getConsultant() {
        return Consultant.builder()
                .id(3L)
                .NF2F(30000)
                .F2F(50000)
                .area("서울시 강남")
                .build();
    }
    private Product getProduct(Consultant consultant) {
        return Product.builder()
                .Id(1L)
                .consultant(getConsultant())
                .status("예약 가능")
                .reservationAt(LocalDateTime.now().plusDays(1))
                .area("서울시 강남")
                .price(0)
                .build();
    }
}
