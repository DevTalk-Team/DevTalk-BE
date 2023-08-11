//package com.devtalk.product.productservice.unittest.validator;
//
//import com.devtalk.product.productservice.product.application.RegistService;
//import com.devtalk.product.productservice.product.application.port.in.dto.RegistProdReq;
//import com.devtalk.product.productservice.product.application.port.out.repository.ConsultantQueryableRepo;
//import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
//import com.devtalk.product.productservice.product.application.validator.Validator;
//import com.devtalk.product.productservice.product.domain.member.Consultant;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.mockito.BDDMockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class RegistServiceUnitTest {
//
//    @InjectMocks
//    RegistService registService;
//
//    @Mock
//    Validator validator;
//    @Mock
//    ProductRepo productRepo;
//    @Mock
//    ConsultantQueryableRepo consultantQueryableRepo;
//
//    @Test
//    @DisplayName("s. 예약성공")
//     void 상품등록성공(){
//        System.out.printf("등록시작");
//        // given
//        Consultant consultant = getConsultant();
//
//        RegistProdReq registProdReq = getRegistProdReq(consultant.getId());
//
//        willDoNothing().given(validator).validate(registProdReq);
//        given(consultantQueryableRepo.findByConsultantId(consultant.getId())).willReturn(Optional.of(consultant));
//
//        //when
//        registService.registProduct(registProdReq);
//
//        //then
//        then(productRepo).should(times(1)).save(any());
//    }
//
//    private RegistProdReq getRegistProdReq(Long consultantId) {
//        return RegistProdReq.builder()
//                .consultantId(consultantId)
//                .reservationAt(LocalDateTime.now().plusDays(1))
//                .type(7)
//                .status("등록 완료")
//                .build();
//    }
//
//    private Consultant getConsultant() {
//        return Consultant.builder()
//                .id(3L)
//                .loginId("consultant@example.com")
//                .name("상담자1")
//                .build();
//    }
//}
