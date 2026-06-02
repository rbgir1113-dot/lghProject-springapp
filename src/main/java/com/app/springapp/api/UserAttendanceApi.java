package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.dto.response.UserAttendanceSummaryResponseDTO;
import com.app.springapp.service.edu.UserAttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class UserAttendanceApi {
    private final UserAttendanceService userAttendanceService;

    @PostMapping("/users/{userId}")
    @Operation(summary = "오늘 출석 등록", description = "사용자의 오늘 출석을 등록합니다. 같은 날짜에는 한 번만 등록할 수 있습니다.")
    @ApiResponse(responseCode = "200", description = "오늘 출석 등록 성공")
    @ApiResponse(responseCode = "409", description = "이미 오늘 출석을 완료한 사용자")
    @Parameter(
            name = "userId",
            description = "유저 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> checkIn(@PathVariable Long userId) {
        userAttendanceService.checkIn(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "오늘 출석이 완료되었습니다.", null)
        );
    }


    @GetMapping("/users/{userId}/summary")
    @Operation(summary = "출석 현황 조회", description = "오늘 출석 여부, 누적 출석일, 연속 출석일과 요청 기간의 출석 날짜를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "출석 현황 조회 성공")
    @Parameter(
            name = "userId",
            description = "유저 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "startDate",
            description = "조회 시작일",
            required = true,
            in = ParameterIn.QUERY,
            example = "2026-06-01",
            schema = @Schema(type = "string", format = "date")
    )
    @Parameter(
            name = "endDate",
            description = "조회 종료일: 해당 날짜는 포함하지 않음",
            required = true,
            in = ParameterIn.QUERY,
            example = "2026-07-01",
            schema = @Schema(type = "string", format = "date")
    )
    public ResponseEntity<ApiResponseDTO> getSummary(
            @PathVariable Long userId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        UserAttendanceSummaryResponseDTO summary =
                userAttendanceService.getAttendanceSummary(userId, startDate, endDate);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "출석 현황 조회 성공", summary)
        );
    }
}
