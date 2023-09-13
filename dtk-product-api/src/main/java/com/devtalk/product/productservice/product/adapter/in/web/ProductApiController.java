package com.devtalk.product.productservice.product.adapter.in.web;


import com.devtalk.product.productservice.global.vo.SuccessResponse;
//import com.devtalk.product.productservice.product.adapter.in.web.dto.MemberInput;
import com.devtalk.product.productservice.product.adapter.in.web.dto.ProductInput;
import com.devtalk.product.productservice.product.adapter.in.web.dto.ProductOutput;
//import com.devtalk.product.productservice.product.adapter.out.web.producer.KafkaProducer;

//import com.devtalk.product.productservice.product.application.port.in.dto.ConsultantReq;
//import com.devtalk.product.productservice.product.application.port.in.dto.ConsulterReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductRes;
//import com.devtalk.product.productservice.product.application.port.in.dto.ProductReservedDetailsReq;
//import com.devtalk.product.productservice.product.application.port.in.member.SignUpUseCase;
import com.devtalk.product.productservice.product.application.port.in.product.*;
//import com.devtalk.product.productservice.product.domain.member.Consultant;
//import com.devtalk.product.productservice.product.domain.member.MemberType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@Tag(name = "상품 서비스", description = "데브톡 - 상품 서비스 REST API")
@EnableDiscoveryClient
@RestController
@Slf4j
@RequestMapping("/products")
@RequiredArgsConstructor

class ProductApiController {
    //private final DeleteUseCase deleteUseCase;
    private final RegistUseCase registUseCase;
    private final SearchUseCase searchUseCase;
    private final UpdateUseCase updateUseCase;
    //private final ReserveUseCase reserveUseCase;


    //private final KafkaProducer kafkaProducer;
    private final Environment env;

    //private final SignUpUseCase signUpUseCase;



    @GetMapping("/welcome")
    public String welcome(){
        return ("hello");
    }


    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }



    @Operation(summary = "상품 등록 API", description = "상담사가 상담 가능한 시간을 설정.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상품 등록 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "상품 등록 실패",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/consultant/{consultantId}/register")
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
    @GetMapping("/search/consultant/{consultantId}")
    public ResponseEntity<ProductOutput> searchProductList(@PathVariable Long consultantId) {
        List<ProductRes.ConsultantProductListRes> consultantProductListRes = searchUseCase.searchList(consultantId);
        ProductOutput productOutput
                = new ProductOutput("0502", "조회 성공", consultantProductListRes);
        return ResponseEntity.status(HttpStatus.OK).body(productOutput);
    }

    //상품 수정 - 완료
    @Operation(summary = "상담 진행 방식 수정 API", description = "상담 진행 방식을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "예약 가능 상품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "예약 가능 상품 조회 실패",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@RequestBody @Validated ProductInput.UpdateInput updateInput,
                                           @PathVariable Long productId) {
        updateUseCase.updateProductType(updateInput.toReq(productId));
        return ResponseEntity.ok().build();
    }

//    //예약 상품 삭제 - 완료
//    @Operation(summary = "예약 상품 삭제 API", description = "예약된 상담을 삭제한다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "예약 상품 삭제 성공",
//                    content = @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = SuccessResponse.class))),
//            @ApiResponse(responseCode = "401", description = "예약 상품 삭제 실패",
//                    content = @Content(mediaType = "application/json"))
//    })
//    @DeleteMapping("delete/{consultationId}")
//    public ResponseEntity<?> deleteProduct(@PathVariable Long consultationId) {
//        deleteUseCase.deleteReservation(consultationId);
//        return ResponseEntity.ok().build();
//    }
//
//    //마이페이지 예약 리스트 조회 - 완료
//    @Operation(summary = "마이페이지 예약 리스트 조회 API", description = "나의 예약된 상담을 조회한다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "마이페이지 예약 리스트 조회 성공",
//                    content = @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = SuccessResponse.class))),
//            @ApiResponse(responseCode = "401", description = "마이페이지 예약 리스트 조회 실패",
//                    content = @Content(mediaType = "application/json"))
//    })
//    @GetMapping("search/memberId/{memberId}")
//    public ResponseEntity<ProductOutput> myConsultationList(@PathVariable Long memberId) {
//        List<ProductRes.ReservedProductRes> myConsultatioinListRes = searchUseCase.searchConsulationListByMemberId(memberId);
//        ProductOutput productOutput
//                = new ProductOutput("0500", "조회 성공", myConsultatioinListRes);
//        return ResponseEntity.status(HttpStatus.OK).body(productOutput);
//    }


    //상담자등록
//    @PostMapping("/regist/consultant")
//    public ResponseEntity<?> registConsultant(@RequestBody MemberInput.ConsultantInput consultantInput) {
//    signUpUseCase.signupConsultant(consultantInput.toReq());
//    return ResponseEntity.ok().build();
//    }
//    //내담자등록
//    @PostMapping("/regist/consulter")
//    public ResponseEntity<?> registConsulter(@RequestBody ConsulterReq consulterReq) {
//        signUpUseCase.signupConsulter(consulterReq);
//        return ResponseEntity.ok().build();
//    }
//
//    //상담예약
//    @PostMapping("/reserveConsultation")
//    public ResponseEntity<?> reserveConsultation(@RequestBody ProductReservedDetailsReq productReservedDetailsReq) {
//        reserveUseCase.reserveProduct(productReservedDetailsReq);
//        return ResponseEntity.ok().build();
//    }
}