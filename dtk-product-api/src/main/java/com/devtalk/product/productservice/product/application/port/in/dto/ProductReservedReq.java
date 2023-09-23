package com.devtalk.product.productservice.product.application.port.in.dto;

//import com.devtalk.product.productservice.product.domain.product.ReservedProceedType;
import com.devtalk.product.productservice.product.domain.product.ProcessStatus;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProductReservedReq {
    private Long productId;
    private ProcessStatus status;
    //    private LocalDateTime reservationAt;
}

