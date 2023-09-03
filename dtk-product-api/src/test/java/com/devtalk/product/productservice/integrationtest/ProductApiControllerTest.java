//package com.devtalk.product.productservice.integrationtest;
//
//import com.devtalk.product.productservice.integrationtest.setup.ConsultantSetUp;
//import com.devtalk.product.productservice.product.adapter.in.web.ProductApiController;
//import com.devtalk.product.productservice.product.application.ProductService;
//import com.devtalk.product.productservice.product.application.port.out.repository.ConsultantQueryableRepo;
//import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
//import com.devtalk.product.productservice.product.application.validator.Validator;
//import com.devtalk.product.productservice.product.domain.member.Consultant;
//import com.devtalk.product.productservice.product.domain.product.Product;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//import static com.devtalk.product.productservice.product.adapter.in.web.dto.ProductInput.*;
//import static com.devtalk.product.productservice.product.domain.product.ProductProceedType.NF2F;
//
//@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Transactional
//@ExtendWith(MockitoExtension.class)
//
//public class ProductApiControllerTest {
//    private ProductApiController productApiController;
//
//
//    private Product product;
//    @Autowired
//    private  Validator validator;
//    @Autowired
//    private ProductRepo productRepo;
//    @Autowired
//    private ConsultantQueryableRepo consultantQueryableRepo;
//    @Autowired
//    private ConsultantSetUp consultantSetUp;
//
//    @BeforeAll
//    void setUp(){
//        productApiController = new ProductApiController(new ProductService(product, validator, consultantQueryableRepo, productRepo));
//    }
//
//    @Test
//    @DisplayName("s 상품 등록 성공")
//    void 상품등록성공() throws Exception{
//
//        //given
//        Consultant consultant = consultantSetUp.saveConsultant(1L, 30000, 50000, "서울시 강");
//
//        RegistrationInput registrationInput = RegistrationInput.builder()
//                .consultantId(consultant.getId())
//                .status("예약 가능")
//                .reservationAt(LocalDateTime.now().plusDays(1))
//                .type(NF2F)
//                .area(" ")
//                .price(0)
//                .build();
//
//        //when
//        productApiController.registProduct(registrationInput, consultant.getId());
//
//        //then
//
//    }
//}
