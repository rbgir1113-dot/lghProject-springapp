package com.app.springapp.mapper;

import com.app.springapp.domain.dto.PaymentDTO;
import com.app.springapp.domain.vo.PaymentVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PaymentMapper {
    // 결제 저장
    void insert(PaymentVO paymentVO);

    // 내 결제 목록 조회
    List<PaymentDTO> selectByUserId(Long userId);

    // 주문번호로 결제 조회
    PaymentDTO selectByOrderId(String orderId);
}
