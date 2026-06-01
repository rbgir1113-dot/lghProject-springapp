package com.app.springapp.service;

import com.app.springapp.domain.dto.PaymentDTO;
import com.app.springapp.domain.dto.request.PaymentConfirmRequestDTO;
import com.app.springapp.domain.dto.request.PaymentReadyRequestDTO;
import java.util.List;
import java.util.Map;

public interface PaymentService {
    // 결제 준비 (orderId 생성)
    Map<String, Object> ready(PaymentReadyRequestDTO requestDTO, Long userId);

    // 결제 승인 (토스 서버 승인 + DB 저장)
    PaymentDTO confirm(PaymentConfirmRequestDTO requestDTO, Long userId);

    // 내 결제 목록 조회
    List<PaymentDTO> getMyPayments(Long userId);
}
