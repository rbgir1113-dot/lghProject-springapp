package com.app.springapp.repository;

import com.app.springapp.domain.dto.PaymentDTO;
import com.app.springapp.domain.vo.PaymentVO;
import com.app.springapp.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentDAO {
    private final PaymentMapper paymentMapper;

    // 결제 저장
    public void save(PaymentVO paymentVO) {
        paymentMapper.insert(paymentVO);
    }

    // 내 결제 목록 조회
    public List<PaymentDTO> findByUserId(Long userId) {
        return paymentMapper.selectByUserId(userId);
    }

    // 주문번호로 결제 조회
    public Optional<PaymentDTO> findByOrderId(String orderId) {
        return Optional.ofNullable(paymentMapper.selectByOrderId(orderId));
    }
}
