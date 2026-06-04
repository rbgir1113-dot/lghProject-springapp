package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "마이페이지 자격증 목록 응답 DTO")
public class MyPageCertificateItemResponseDTO {

    @Schema(description = "시험 결과 번호", example = "1")
    private Long testResultId;

    @Schema(description = "자격증명", example = "수어 통역사 2급")
    private String testTitle;

    @Schema(description = "취득일자", example = "2025-03-08T10:00:00")
    private LocalDateTime acquiredAt;

    @Schema(description = "실물 신청 상태 코드", example = "processing")
    private String certApplyStatus;

    @Schema(description = "실물 신청 상태명", example = "신청대기")
    private String certApplyStatusName;

    @Schema(description = "자격증 실물 신청 번호", example = "1")
    private Long certRenewId;

    @Schema(description = "실물 신청 가능 여부", example = "true")
    private boolean canApply;

    @Schema(description = "신청 버튼 문구", example = "신청하기")
    private String buttonText;
}