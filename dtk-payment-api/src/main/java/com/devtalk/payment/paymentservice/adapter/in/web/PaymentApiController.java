package com.devtalk.payment.paymentservice.adapter.in.web;

import com.devtalk.payment.global.code.SuccessCode;
import com.devtalk.payment.global.vo.SuccessResponse;
import com.devtalk.payment.global.vo.SuccessResponseWithoutResult;
import com.devtalk.payment.paymentservice.adapter.in.web.dto.ConsultationInput;
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
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.payment.paymentservice.adapter.in.web.dto.PaymentOutput.PaymentSearchOutput;
import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentReq.WebhookReq;

@Tag(name = "결제 서비스", description = "데브톡 - 결제 서비스 REST API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/payment")
class PaymentApiController {
    private final PaymentUseCase paymentUseCase;
    private final RefundUseCase refundUseCase;
    private final ModelMapper modelMapper;

    // 결제 링크 요청
    @Operation(summary = "결제 링크 생성 API", description = "해당 예약건의 결제 링크를 생성합니다.",
            responses = {@ApiResponse(description = "Successful Operation", responseCode = "200",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = SuccessResponse.class)))})
    @GetMapping("/{consultationId}/link")
    public ResponseEntity<?> getPaymentLink(@PathVariable Long consultationId,
                                            @RequestParam Long userId){
        return SuccessResponse.toResponseEntity(SuccessCode.GET_PAYMENT_LINK_SUCCESS,
                paymentUseCase.getPaymentLink(consultationId, userId));
    }

    @PostMapping("/webhook")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void portoneWebhook(@RequestBody WebhookInput webhookInput) {
        WebhookReq webhookReq = modelMapper.map(webhookInput, WebhookReq.class);
        paymentUseCase.updatePaymentStatus(webhookReq);
        log.info(webhookInput.getImp_uid());
    }

    // 결제 정보 조회
    @Operation(summary = "결제 정보 조회 API", description = "해당 예약건의 결제 정보를 조회합니다.",
            responses = {@ApiResponse(description = "Successful Operation", responseCode = "200",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = SuccessResponse.class)))})
    @GetMapping("/{consultationId}/info")
    public ResponseEntity<?> getPaymentInfo(@PathVariable("consultationId") Long consultationId){
        return SuccessResponse.toResponseEntity(SuccessCode.GET_PAYMENT_INFO_SUCCESS,
                                                paymentUseCase.searchPaymentInfo(consultationId));
    }

    @Operation(summary = "결제 취소 API", description = "해당 예약건의 결제를 취소합니다.",
            responses = {@ApiResponse(description = "Successful Operation", responseCode = "200",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = SuccessResponseWithoutResult.class)))})
    @DeleteMapping("/{consultationId}/cancel")
    public ResponseEntity<?> cancelPayment(@PathVariable("consultationId") Long consultationId){
        refundUseCase.cancelPayment(consultationId);
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.REFUND_REQUEST_SUCCESS);
    }


    @Operation(summary = "[테스트]결제 서비스 - 임시 결제 정보 생성", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @PostMapping("/test")
    public ResponseEntity<?> insertPaymentInfo(@RequestBody ConsultationInput consultationInput) {
//        String userEmail= request.getHeader("User-Email");
//        String userEmail = memberDetails.getUsername();
//        log.info("User-Email : {}", userEmail);
        paymentUseCase.createPaymentInfo(consultationInput);
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CREATE_TEMP_PAYMENT_INFO_SUCCESS);
    }
}
