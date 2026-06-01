package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "결제 준비 요청 DTO")
public class PaymentReadyRequestDTO {
    @Schema(description = "결제 유형 (TEST_APPLY / CERT_RENEW / PASS_PRINT)")
    private String paymentType;
    @Schema(description = "결제 대상 ID (시험신청ID / 자격증갱신ID / 시험결과ID)")
    private Long referenceId;
    @Schema(description = "결제 금액")
    private Long paymentAmount;
    @Schema(description = "주문명")
    private String orderName;
}
