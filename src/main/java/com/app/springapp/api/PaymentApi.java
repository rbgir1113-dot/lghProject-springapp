package com.app.springapp.api;

import com.app.springapp.domain.dto.PaymentDTO;
import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.request.PaymentConfirmRequestDTO;
import com.app.springapp.domain.dto.request.PaymentReadyRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@Slf4j
public class PaymentApi {

    private final PaymentService paymentService;

    // 결제 준비 - orderId 생성
    @PostMapping("/ready")
    @Operation(summary = "결제 준비", description = "orderId 생성 후 프론트에 반환")
    public ResponseEntity<ApiResponseDTO> ready(
            @RequestBody PaymentReadyRequestDTO requestDTO,
            Authentication authentication) {

        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Map<String, Object> result = paymentService.ready(requestDTO, userDTO.getId());
        return ResponseEntity.ok(ApiResponseDTO.of(true, "결제 준비 완료", result));
    }

    // 결제 승인
    @PostMapping("/confirm")
    @Operation(summary = "결제 승인", description = "토스 서버 승인 요청 + DB 저장")
    public ResponseEntity<ApiResponseDTO> confirm(
            @RequestBody PaymentConfirmRequestDTO requestDTO,
            Authentication authentication) {

        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        PaymentDTO paymentDTO = paymentService.confirm(requestDTO, userDTO.getId());
        return ResponseEntity.ok(ApiResponseDTO.of(true, "결제 승인 완료", paymentDTO));
    }

    // 내 결제 목록 조회
    @GetMapping("/my")
    @Operation(summary = "내 결제 목록 조회")
    public ResponseEntity<ApiResponseDTO> getMyPayments(Authentication authentication) {

        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        List<PaymentDTO> list = paymentService.getMyPayments(userDTO.getId());
        return ResponseEntity.ok(ApiResponseDTO.of(true, "조회 성공", list));
    }
}
