package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "마이페이지 자격증 실물 신청 완료 응답 DTO")
public class MyPageCertificateApplyCompleteResponseDTO {

    @Schema(description = "자격증 실물 신청 번호", example = "1")
    private Long certRenewId;

    @Schema(description = "시험 결과 번호", example = "1")
    private Long testResultId;

    @Schema(description = "자격증명", example = "수어 통역사 2급")
    private String testTitle;

    @Schema(description = "취득일자", example = "2025-03-08T10:00:00")
    private LocalDateTime acquiredAt;

    @Schema(description = "신청 상태 코드", example = "processing")
    private String certApplyStatus;

    @Schema(description = "신청 상태명", example = "신청대기")
    private String certApplyStatusName;

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
}