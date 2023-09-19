package com.devtalk.payment.paymentservice.adapter.in.web;

import com.devtalk.payment.global.code.SuccessCode;
import com.devtalk.payment.global.vo.SuccessResponse;
import com.devtalk.payment.global.vo.SuccessResponseWithoutResult;
import com.devtalk.payment.paymentservice.adapter.in.web.dto.PaymentInput.WebhookInput;
import com.devtalk.payment.paymentservice.application.port.in.PaymentUseCase;
import com.devtalk.payment.paymentservice.application.port.in.RefundUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.payment.paymentservice.adapter.in.web.dto.PaymentOutput.PaymentSearchOutput;
import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentReq.WebhookReq;

/*
 * 웹 어댑터의 책임
 * - http 요청을 자바 객체로 매핑하기
 * - 권한 검사 (validator에 넘김)
 * - 입력 유효성 검증하기
 * - 입력을 유즈 케이스의 입력 모델로 매핑하기
 * - 유즈 케이스 호출하기
 * - 유즈 케이스의 출력을 http로 매핑하기
 * - http 응답을 반환하기
 * */
@Tag(name = "결제 서비스", description = "데브톡 - 결제 서비스 REST API")
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/payment")
class PaymentApiController {
    private final PaymentUseCase paymentUseCase;
    private final RefundUseCase refundUseCase;
    private final ModelMapper modelMapper;

    // 결제 링크 요청
    @Operation(summary = "결제 링크 생성 API", description = "해당 예약건의 결제 링크를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "결제 링크 생성 성공",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰 발급 실패",
                        content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "유효하지 않은 예약ID 입니다.",
                        content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{consultationId}/link")
    public ResponseEntity<?> getPaymentLink(@PathVariable("consultationId") Long consultationId){
        String paymentLink = paymentUseCase.getPaymentLink(consultationId);

        return SuccessResponse.toResponseEntity(SuccessCode.GET_PAYMENT_LINK_SUCCESS, paymentLink);
    }

    // TODO : TEST: 결제 버튼 페이지
    @GetMapping("/{consultationId}")
    public String payment(@PathVariable("consultationId") Long consultationId, Model model){
        String paymentLink = paymentUseCase.getPaymentLink(consultationId);
        model.addAttribute("paymentLink", paymentLink);

        return "payment-button";
    }

    @PostMapping("/webhook")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void portoneWebhook(@RequestBody WebhookInput webhookInput) {
        WebhookReq webhookReq = modelMapper.map(webhookInput, WebhookReq.class);
        paymentUseCase.updatePaymentStatus(webhookReq);
        log.info(webhookInput.getImp_uid());
    }

    // 결제 정보 조회
    @Operation(summary = "결제 정보 조회 API", description = "해당 예약건의 결제 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentSearchOutput.class))),
            @ApiResponse(responseCode = "409", description = "유효하지 않은 예약ID 입니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{consultationId}/info")
    public ResponseEntity<?> getPaymentInfo(@PathVariable("consultationId") Long consultationId){
        return SuccessResponse.toResponseEntity(SuccessCode.GET_PAYMENT_INFO_SUCCESS,
                                                paymentUseCase.searchPaymentInfo(consultationId));
    }

    @Operation(summary = "결제 취소 API", description = "해당 예약건의 결제를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 취소 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "409", description = "유효하지 않은 예약ID 입니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{consultationId}/cancel")
    public ResponseEntity<?> cancelPayment(@PathVariable("consultationId") Long consultationId){
        refundUseCase.cancelPayment(consultationId);
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.REFUND_REQUEST_SUCCESS);
    }


//    private final MemberServiceClient memberServiceClient;
//    //test
//    @GetMapping("/test/{memberId}")
//    public void memberInfo(@PathVariable Long memberId) {
//        MemberRes<MemberRes.MemberInfoRes> consulter = memberServiceClient.getMemberInfo(memberId);
//        log.info(consulter.toString());
//    }
}
