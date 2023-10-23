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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;




@Tag(name = "상품 서비스", description = "데브톡 - 상품 서비스 REST API")
@EnableDiscoveryClient
@RestController
@Slf4j
@RequestMapping("/product")
@RequiredArgsConstructor

class ProductApiController {
    private final RegistUseCase registUseCase;
    private final SearchUseCase searchUseCase;
    private final UpdateUseCase updateUseCase;
    private final AuthUseCase authUseCase;
    private final DeleteUseCase deleteUseCase;


    private final Environment env;

    //private final SignUpUseCase signUpUseCase;



    @GetMapping("/welcome")
    public String welcome(){
        return ("hello");
    }


    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Product Service"
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
    @PostMapping("/regist/products")
    public ResponseEntity<?> registProduct(@RequestBody @Validated ProductInput.RegistrationInput registrationInput,
                                           @RequestHeader(value = "User-Email") String userEmail) {
        log.info("User-Eamil : {}", userEmail);
        Long consultantId = authUseCase.auth(userEmail);
        try {
            registUseCase.registProduct(consultantId,registrationInput.toReq());

            return ResponseEntity.ok().build();

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date or time format");
        }
    }

    @Operation(summary = "상담사 예약 가능 상품 전체 조회 API", description = "상담자의 모든 상담 상품을 확인한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "예약 가능 상품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "예약 가능 상품 조회 실패",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/search/consultants/{memberId}")
    public ResponseEntity<ProductOutput> searchProductList(@PathVariable Long memberId) {
        List<ProductRes.ConsultantProductListRes> consultantProductListRes = searchUseCase.searchList(memberId);
        ProductOutput productOutput
                = new ProductOutput("0502", "조회 성공", consultantProductListRes);
        return ResponseEntity.status(HttpStatus.OK).body(productOutput);
    }

    @Operation(summary = "상담사 예약 가능 상품 날짜별 조회 API", description = "내담자가 상담을 원하는 날짜의 상담자의 예약 가능 시간을 확인한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "해당 날짜 상품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "해당 날짜 상품 조회 실패",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/search/consultants/{memberId}/Date/{date}")
    public ResponseEntity<ProductOutput> searchProductByDateList(@PathVariable Long memberId, @PathVariable LocalDateTime date) {
        List<ProductRes.ConsultantProductListRes> consultantProductListRes = searchUseCase.searchProductByDateList(memberId,date);
        ProductOutput productOutput
                = new ProductOutput("0502", "조회 성공", consultantProductListRes);
        return ResponseEntity.status(HttpStatus.OK).body(productOutput);
    }

    //상품 조회 - 완료
    @Operation(summary = "상품 조회 API", description = "상담 상품을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "예약 가능 상품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "예약 가능 상품 조회 실패",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/search/productInfo")
    public ResponseEntity<?> searchProduct(@RequestBody @Validated ProductInput.SearchInput searchInput) {
        ProductRes.ProductDetailsRes productDetailsRes = searchUseCase.searchProduct(searchInput.toReq());
        ProductOutput productOutput
                = new ProductOutput("0502", "조회 성공", productDetailsRes);
        return ResponseEntity.status(HttpStatus.OK).body(productOutput);
    }

    //상품 수정 - 완료
    @Operation(summary = "상담 진행 방식 수정 API", description = "상담 진행 방식을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상품 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "상품 수정 실패",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/update/products")
    public ResponseEntity<?> updateProduct(@RequestBody @Validated ProductInput.UpdateInput updateInput,
                                           @RequestHeader(value = "User-Email") String userEmail) {
        log.info("User-Eamil : {}", userEmail);
        Long consultantId = authUseCase.auth(userEmail);
        try {
            updateUseCase.updateProductType(consultantId,updateInput.toReq());
            return ResponseEntity.ok().build();
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date or time format");
        }
    }

    //상품 삭제 - 완료
    @Operation(summary = "상담 상품 삭제 API", description = "상담 상품을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상품 삭제 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "상품 삭제 실패",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/delete/prodocuts")
    public ResponseEntity<?> deleteProduct(@RequestBody @Validated ProductInput.DeleteInput deleteInput,
                                           @RequestHeader(value = "User-Email") String userEmail) {
        log.info("User-Eamil : {}", userEmail);
        Long consultantId = authUseCase.auth(userEmail);
        try {
            deleteUseCase.deleteProduct(consultantId,deleteInput.toReq());
            return ResponseEntity.ok().build();
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date or time format");
        }
    }
}