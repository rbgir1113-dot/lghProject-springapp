package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.UserAttendanceSummaryResponseDTO;

import java.time.LocalDate;

public interface UserAttendanceService {

    // 오늘 출석 등록
    public void checkIn(Long userId);

    // 출석 현황 조회, 달력과 연속 출석 현황 조회
    public UserAttendanceSummaryResponseDTO getAttendanceSummary(Long userId, LocalDate startDate, LocalDate endDate);
}
