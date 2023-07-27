package com.devtalk.product.productservice.product.adapter.in.web.dto;

import com.devtalk.product.productservice.product.application.port.in.dto.RegistProdReq;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


public class ProductInput {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class RegistrationInput{

        @NotBlank
        private LocalDateTime reservationAt;

        @NotBlank
        private int type;

        @NotBlank
        private String status;

        @NotBlank
        private List<Integer> buttonStates;


        public RegistProdReq toReq(Long consultantId){
            return RegistProdReq.builder()
                    .consultantId(consultantId)
                    .reservationAt(reservationAt)
                    .type(calculateProductType(buttonStates))
                    .status(status)
                    .build();
        }

        private int calculateProductType(List<Integer> buttonStates) {
            int productType = 0;
            for (int i = 0; i < buttonStates.size(); i++) {
                if (buttonStates.get(i) == 1) {
                    productType += (int) Math.pow(2, i);
                }
            }
            return productType;
        }
    }
}
