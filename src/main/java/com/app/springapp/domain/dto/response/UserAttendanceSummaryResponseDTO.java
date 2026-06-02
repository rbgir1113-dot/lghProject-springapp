package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@Schema(description = "유저 출석 현황 응답 DTO")
public class UserAttendanceSummaryResponseDTO {

    @Schema(description = "오늘 출석 여부", example = "true")
    private boolean checkedToday;

    @Schema(description = "오늘 날짜", example = "2026-06-02")
    private LocalDate currentDate;

    @Schema(description = "오늘 출석 안내", example = "+30 XP 획득했어요!")
    private String todayLabel;

    @Schema(description = "현재 연속 출석일", example = "3")
    private int streakDays;

    @Schema(description = "누적 출석일", example = "14")
    private int totalAttendanceDays;

    @Schema(description = "해당 기간 출석 날짜 목록")
    private List<LocalDate> attendanceDates;

}
