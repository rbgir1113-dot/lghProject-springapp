package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@Schema(description = "마이페이지 자격증 페이지 응답 DTO")
public class MyPageCertificateResponseDTO {

    @Schema(description = "자격증 목록")
    private List<MyPageCertificateItemResponseDTO> certificateList;

    @Schema(description = "자격증 요약 정보")
    private MyPageCertificateSummaryResponseDTO summary;

    @Schema(description = "수강중인 강좌 목록")
    private List<MyPageCertificateCourseResponseDTO> courseList;
}