package com.app.springapp.domain.vo;

import lombok.Data;

@Data
public class PaymentVO {
    private Long id;
    private String paymentOrderId;
    private String paymentKey;
    private Long paymentAmount;
    private String paymentStatus;
    private String paymentMethod;
    private String paymentType;   // TEST_APPLY / CERT_RENEW / PASS_PRINT
    private Long referenceId;     // 각 유형의 대상 ID
    private Long userId;
}
