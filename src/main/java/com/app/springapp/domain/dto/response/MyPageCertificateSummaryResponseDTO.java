package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "마이페이지 자격증 요약 응답 DTO")
public class MyPageCertificateSummaryResponseDTO {

    @Schema(description = "수강중인 강좌 수", example = "4")
    private int courseCount;

    @Schema(description = "보유 자격증 수", example = "3")
    private int certificateCount;

    @Schema(description = "실물 신청 가능 자격증 수", example = "2")
    private int availableApplyCount;

    @Schema(description = "실물 신청 대기 수", example = "1")
    private int processingApplyCount;

    @Schema(description = "실물 신청 완료 수", example = "1")
    private int completedApplyCount;
}