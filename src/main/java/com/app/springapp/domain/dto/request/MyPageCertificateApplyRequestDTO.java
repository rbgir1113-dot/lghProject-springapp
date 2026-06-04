package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "마이페이지 자격증 실물 신청 요청 DTO")
public class MyPageCertificateApplyRequestDTO {

    @Schema(description = "시험 결과 번호", example = "1", required = true)
    private Long testResultId;

    @Schema(description = "수령 방법", example = "우편수령", required = true)
    private String certReceiveType;

    @Schema(description = "수령인 이름", example = "김민준", required = true)
    private String certName;

    @Schema(description = "수령인 전화번호", example = "010-1234-5678", required = true)
    private String certPhone;

    @Schema(description = "우편번호", example = "06236", required = true)
    private String certPostcode;

    @Schema(description = "도로명 주소", example = "서울특별시 강남구 테헤란로 123", required = true)
    private String certRoadAddress;

    @Schema(description = "지번 주소", example = "서울특별시 강남구 역삼동 123-45")
    private String certJibunAddress;

    @Schema(description = "상세 주소", example = "101동 1203호", required = true)
    private String certDetailAddress;

    @Schema(description = "전체 수령 주소", example = "서울특별시 강남구 테헤란로 123, 101동 1203호", required = true)
    private String certAddress;
}