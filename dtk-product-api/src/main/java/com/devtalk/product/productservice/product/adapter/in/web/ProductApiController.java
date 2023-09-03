package com.devtalk.product.productservice.product.adapter.in.web;


import com.devtalk.product.productservice.global.vo.SuccessResponse;
import com.devtalk.product.productservice.product.adapter.in.web.dto.ProductInput;
import com.devtalk.product.productservice.product.adapter.in.web.dto.ProductOutput;
import com.devtalk.product.productservice.product.adapter.out.web.producer.KafkaProducer;
import com.devtalk.product.productservice.product.application.port.in.*;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.ReserveProdReq;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductRes;
import com.devtalk.product.productservice.product.application.port.out.repository.MemberRepo;
import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.member.Member;
import com.devtalk.product.productservice.product.domain.member.MemberType;
import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ProductProceedType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@Tag(name = "상품 서비스", description = "데브톡 - 상품 서비스 REST API")
@EnableDiscoveryClient
@RestController
@Slf4j
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor

class ProductApiController {
    private final MemberRepo memberRepo;
    private final DeleteUseCase deleteUseCase;
    private final RegistUseCase registUseCase;
    private final SearchUseCase searchUseCase;
    private final UpdateUseCase updateUseCase;

    private final KafkaProducer kafkaProducer;



//    @PostMapping("")
//    public ResponseEntity<?> create() {
//        Consultant consultant = Consultant.builder()
//                .name("구한서")
//                .loginId("5시간 비대면 상담")
//                .role(RoleType.CONSULTANT)
//                .F2F(5000)
//                .NF2F(4000)
//                .area("gangnam")
//                .build();
//        memberRepo.save(consultant);
//
//
//        return ResponseEntity.ok().build();
//    }

    @Operation(summary = "상품 등록 API", description = "상담사가 상담 가능한 시간을 설정.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상품 등록 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "상품 등록 실패",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/consultant/{consultantid}/register")
    public ResponseEntity<?> registProduct(@RequestBody @Validated ProductInput.RegistrationInput registrationInput,
                                           @PathVariable Long consultantId) {
        registUseCase.registProduct(registrationInput.toReq(consultantId));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "상담사 예약 가능 상품 조회 API", description = "내담자가 상담을 원하는 상담자의 예약 가능 시간을 확인한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "예약 가능 상품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "예약 가능 상품 조회 실패",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/search/consultant/{consultantid}")
    //@ApiOperation(value = "상품 등록", notes = "상담자가 상담 가능한 시간을 설정한다")
    public ResponseEntity<ProductOutput> searchProductList(@PathVariable Long consultantId) {
        List<ProductRes.ConsultantProductListRes> consultantProductListRes = searchUseCase.searchList(consultantId);
        ProductOutput productOutput
                = new ProductOutput("0500", "조회 성공", consultantProductListRes);
        return ResponseEntity.status(HttpStatus.OK).body(productOutput);
    }

    //상품 수정 - 완료
    //상담 진행 방식을 수정한다.
    @Operation(summary = "상담 진행 방식 수정 API", description = "상담 진행 방식을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "예약 가능 상품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "예약 가능 상품 조회 실패",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/api/products/update/{productId}")
    public ResponseEntity<?> updateProduct(@RequestBody @Validated ProductInput.UpdateInput updateInput,
                                           @PathVariable Long productId) {
        updateUseCase.updateProductType(updateInput.toReq(productId));
        return ResponseEntity.ok().build();
    }

    //예약 상품 삭제 - 완료
    @Operation(summary = "예약 상품 삭제 API", description = "예약된 상담을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "예약 상품 삭제 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "예약 상품 삭제실패",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("delete/{consultationId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long consultationId) {
        deleteUseCase.deleteReservation(consultationId);
        return ResponseEntity.ok().build();
    }



    //마이페이지 예약 리스트 조회 - 완료
    @Operation(summary = "마이페이지 예약 리스트 조회 API", description = "나의 예약된 상담을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "마이페이지 예약 리스트 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "마이페이지 예약 리스트 조회 실패",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("search/memberId/{memberId}")
    public ResponseEntity<ProductOutput> myConsultationList(@PathVariable Long memberId) {
        List<ProductRes.ReservedProductRes> myConsultatioinListRes = searchUseCase.searchConsulationListByMemberId(memberId);
        ProductOutput productOutput
                = new ProductOutput("0500", "조회 성공", myConsultatioinListRes);
        return ResponseEntity.status(HttpStatus.OK).body(productOutput);
    }

//    //상품예약API - 완료
//    @PostMapping("/productId/{productId}/")
//    public ResponseEntity<?> reserveProduct(@RequestBody @Validated ProductInput.ReservationInput reservationInput,
//                                            @PathVariable Long productId)
//    {
//        productUseCase.reserveProduct(reservationInput.toReq(productId));
//        return ResponseEntity.ok().build();
//    }
//
//    //예약상품조회API - 완료
//    @GetMapping("search?consultation={consultationId}")
//    public ResponseEntity<?> searchProduct(@PathVariable Long consultationId)
//    {
//        productUseCase.searchReservedDetatils(consultationId);
//        return ResponseEntity.ok().build();
//    }
//
@PostMapping("")
    public ResponseEntity<?> reserveConsultation() {
         Product product = Product.builder()
                .status("예약 대기중")
                .type(ProductProceedType.NF2F)
                .build();

				//*** 주입 받은 KafkaProducer를 사용 ***//
        kafkaProducer.sendPaymentStatus("test", product);

        return ResponseEntity.ok().build();
    }

}