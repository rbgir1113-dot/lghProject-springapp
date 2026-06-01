package com.app.springapp.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "결제 DTO")
public class PaymentDTO {
    @Schema(description = "결제 번호")
    private Long id;
    @Schema(description = "주문 번호")
    private String paymentOrderId;
    @Schema(description = "토스 결제 승인키")
    private String paymentKey;
    @Schema(description = "결제 금액")
    private Long paymentAmount;
    @Schema(description = "결제 상태 (READY / DONE / CANCELED)")
    private String paymentStatus;
    @Schema(description = "결제 수단")
    private String paymentMethod;
    @Schema(description = "결제 일시")
    private LocalDateTime paymentAt;
    @Schema(description = "결제 유형 (TEST_APPLY / CERT_RENEW / PASS_PRINT)")
    private String paymentType;
    @Schema(description = "결제 대상 ID")
    private Long referenceId;
    @Schema(description = "유저 번호")
    private Long userId;
}
