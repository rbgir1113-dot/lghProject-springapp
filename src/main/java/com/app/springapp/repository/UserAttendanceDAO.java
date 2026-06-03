package com.app.springapp.repository;

import com.app.springapp.domain.vo.UserAttendanceVO;
import com.app.springapp.mapper.UserAttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserAttendanceDAO {
    private final UserAttendanceMapper userAttendanceMapper;

    // 오늘 출석 여부 조회
    public boolean existsTodayAttendance(Long userId) {
        return userAttendanceMapper.countTodayAttendance(userId) > 0;
    }

    // 오늘 출석 등록
    public Long saveTodayAttendance(Long userId) {
        UserAttendanceVO userAttendanceVO = new UserAttendanceVO();
        userAttendanceVO.setUserId(userId);
        userAttendanceMapper.insertTodayAttendance(userAttendanceVO);

        return userAttendanceVO.getId();
    }

    // 오늘 출석 기록 번호 조회
    public Long findTodayAttendanceId(Long userId) {
        return userAttendanceMapper.selectTodayAttendanceId(userId);
    }

    // 누적 출석일 조회
    public int findTotalAttendance(Long userId) {
        return userAttendanceMapper.countTotalAttendance(userId);
    }

    // 현재 연속 출석일 조회
    public int findConsecutiveDays(Long userId) {
        return userAttendanceMapper.selectConsecutiveDays(userId);
    }

    // 기간별 출석 날짜 조회
    public List<LocalDate> findAttendanceDatesByPeriod(Long userId, LocalDate start, LocalDate end) {
        return userAttendanceMapper.selectAttendanceDatesByPeriod(userId, start, end);
    }
}
