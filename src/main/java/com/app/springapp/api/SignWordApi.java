package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.edu.SignWordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sign-words")
public class SignWordApi {

    private final SignWordService signWordService;

    //  GET /api/sign-words?keyword=승려
    //  → DB에서 검색 ->  → React에 반환
    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "수어 검색", description = "DB에 저장된 수어 정보를 검색합니다.")
    @ApiResponse(responseCode = "200", description = "수어 검색 성공")
    public ResponseEntity<ApiResponseDTO> getSignWords(
            @RequestParam(defaultValue = "") String keyword
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "수어 검색 성공", signWordService.getSignWordsByKeyword(keyword)));
    }

    //  POST /api/sign-words/sync
    //  → OpenAPI에서 가져와 DB 저장
    @PostMapping("/sync")
    @Operation(summary = "수어 OpenAPI 동기화", description = "문화공공데이터 OpenAPI에서 수어 데이터를 가져와 DB에 저장합니다.")
    @ApiResponse(responseCode = "200", description = "수어 OpenAPI 동기화 성공")
    public ResponseEntity<ApiResponseDTO> syncSignWords(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "100") int numOfRows
    ) {
        int savedCount = signWordService.syncSignWords(pageNo, numOfRows);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "수어 OpenAPI 동기화 성공", savedCount));
    }

    // GET /api/sign-words/today
    // → 오늘 날짜 기반으로 매일 다른 3개 반환
    @GetMapping("/today")
    @Operation(summary = "오늘의 수어 영상", description = "오늘 날짜 기반으로 매일 다른 수어 영상 3개를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "오늘의 수어 영상 조회 성공")
    public ResponseEntity<ApiResponseDTO> getTodaySignWords() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "오늘의 수어 영상 조회 성공", signWordService.getTodaySignWords()));
    }

    // GET /api/sign-words/weekly-recommendations
    @GetMapping("/weekly-recommendations")
    @Operation(summary = "주간 추천 수어 단어 조회", description = "학습 메인 검색 영역에 보여줄 주간 추천 수어 단어와 이모지를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "주간 추천 수어 단어 조회 성공")
    public ResponseEntity<ApiResponseDTO> getWeeklyRecommendedSignWords() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "주간 추천 수어 단어 조회 성공", signWordService.getWeeklyRecommendedSignWords()));
    }

    @DeleteMapping("/today/cache")
    public ResponseEntity<ApiResponseDTO> clearCache() {
        signWordService.clearTodaySignWordsCache();
        return ResponseEntity.ok(ApiResponseDTO.of(true, "캐시 초기화 완료", null));
    }
}

