package com.app.springapp.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "자격증 실물 수령 주소 VO")
public class CertReceiveAddressVO {

    @Schema(description = "자격증 수령 주소 번호", example = "1")
    private Long id;

    @Schema(description = "우편번호", example = "06236")
    private String certPostcode;

    @Schema(description = "도로명 주소", example = "서울특별시 강남구 테헤란로 123")
    private String certRoadAddress;

    @Schema(description = "지번 주소", example = "서울특별시 강남구 역삼동 123-45")
    private String certJibunAddress;

    @Schema(description = "상세 주소", example = "101동 1203호")
    private String certDetailAddress;

    @Schema(description = "전체 수령 주소", example = "서울특별시 강남구 테헤란로 123, 101동 1203호")
    private String certAddress;

    @Schema(description = "주소 저장 일시", example = "2026-06-04T14:30:00")
    private LocalDateTime certReceiveAddressCreateAt;

    @Schema(description = "자격증 실물 신청 번호", example = "1")
    private Long certRenewId;
}