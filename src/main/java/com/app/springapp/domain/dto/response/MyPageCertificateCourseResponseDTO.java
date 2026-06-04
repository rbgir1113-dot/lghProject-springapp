package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "마이페이지 자격증 수강중인 강좌 응답 DTO")
public class MyPageCertificateCourseResponseDTO {

    @Schema(description = "학습 번호", example = "1")
    private Long eduId;

    @Schema(description = "강좌명", example = "수어 기초 회화")
    private String eduTitle;

    @Schema(description = "강좌 설명", example = "초급자를 위한 수어 기초 회화 강좌입니다.")
    private String eduDetail;

    @Schema(description = "썸네일 URL", example = "https://img.youtube.com/vi/abc123/hqdefault.jpg")
    private String thumbnailUrl;

    @Schema(description = "유튜브 영상 URL", example = "https://www.youtube.com/watch?v=abc123")
    private String youtubeUrl;

    @Schema(description = "진행률", example = "65")
    private int progressPercent;

    @Schema(description = "수강기간", example = "2025.12.31 ~ 2026.12.30")
    private String studyPeriod;
}