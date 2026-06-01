package com.app.springapp.service;

import com.app.springapp.domain.dto.PaymentDTO;
import com.app.springapp.domain.dto.request.PaymentConfirmRequestDTO;
import com.app.springapp.domain.dto.request.PaymentReadyRequestDTO;
import com.app.springapp.domain.vo.PaymentVO;
import com.app.springapp.repository.PaymentDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentDAO paymentDAO;
    private final RestTemplate restTemplate;

    @Value("${toss.secret-key}")
    private String tossSecretKey;

    private static final String TOSS_CONFIRM_URL = "https://api.tosspayments.com/v1/payments/confirm";

    // 결제 준비 - orderId 생성 후 프론트에 반환
    @Override
    public Map<String, Object> ready(PaymentReadyRequestDTO requestDTO, Long userId) {
        String orderId = "ORDER-" + userId + "-" + System.currentTimeMillis();

        Map<String, Object> result = new HashMap<>();
        result.put("orderId", orderId);
        result.put("amount", requestDTO.getPaymentAmount());
        result.put("orderName", requestDTO.getOrderName());
        result.put("paymentType", requestDTO.getPaymentType());
        result.put("referenceId", requestDTO.getReferenceId());
        return result;
    }

    // 결제 승인 - 토스 서버에 승인 요청 후 DB 저장
    @Override
    public PaymentDTO confirm(PaymentConfirmRequestDTO requestDTO, Long userId) {
        // 토스 Basic 인증 헤더 생성
        String encodedKey = Base64.getEncoder()
                .encodeToString((tossSecretKey + ":").getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encodedKey);

        // 토스 승인 요청 바디
        Map<String, Object> body = new HashMap<>();
        body.put("paymentKey", requestDTO.getPaymentKey());
        body.put("orderId", requestDTO.getOrderId());
        body.put("amount", requestDTO.getAmount());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // 토스 서버 승인 요청
        ResponseEntity<Map> response = restTemplate.exchange(
                TOSS_CONFIRM_URL, HttpMethod.POST, entity, Map.class);

        Map<String, Object> tossResponse = response.getBody();
        log.info("토스 승인 응답: {}", tossResponse);

        // DB 저장
        PaymentVO paymentVO = new PaymentVO();
        paymentVO.setPaymentOrderId(requestDTO.getOrderId());
        paymentVO.setPaymentKey(requestDTO.getPaymentKey());
        paymentVO.setPaymentAmount(requestDTO.getAmount());
        paymentVO.setPaymentStatus("DONE");
        paymentVO.setPaymentMethod((String) tossResponse.get("method"));
        paymentVO.setPaymentType(requestDTO.getPaymentType());
        paymentVO.setReferenceId(requestDTO.getReferenceId());
        paymentVO.setUserId(userId);
        paymentDAO.save(paymentVO);

        // 응답 DTO 반환
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentOrderId(paymentVO.getPaymentOrderId());
        paymentDTO.setPaymentKey(paymentVO.getPaymentKey());
        paymentDTO.setPaymentAmount(paymentVO.getPaymentAmount());
        paymentDTO.setPaymentStatus(paymentVO.getPaymentStatus());
        paymentDTO.setPaymentMethod(paymentVO.getPaymentMethod());
        paymentDTO.setPaymentType(paymentVO.getPaymentType());
        paymentDTO.setReferenceId(paymentVO.getReferenceId());
        paymentDTO.setUserId(userId);
        return paymentDTO;
    }

    // 내 결제 목록 조회
    @Override
    public List<PaymentDTO> getMyPayments(Long userId) {
        return paymentDAO.findByUserId(userId);
    }
}
