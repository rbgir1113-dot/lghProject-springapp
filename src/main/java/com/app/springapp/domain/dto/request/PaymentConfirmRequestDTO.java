package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "결제 승인 요청 DTO")
public class PaymentConfirmRequestDTO {
    @Schema(description = "토스 결제 키")
    private String paymentKey;
    @Schema(description = "주문 번호")
    private String orderId;
    @Schema(description = "결제 금액")
    private Long amount;
    @Schema(description = "결제 유형 (TEST_APPLY / CERT_RENEW / PASS_PRINT)")
    private String paymentType;
    @Schema(description = "결제 대상 ID")
    private Long referenceId;
}
