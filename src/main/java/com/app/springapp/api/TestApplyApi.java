package com.app.springapp.api;

import com.app.springapp.domain.dto.MyTestResultDTO;
import com.app.springapp.domain.dto.TestApplyDTO;
import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.TestApplyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test-applications")
@Slf4j
public class TestApplyApi {

    private final TestApplyService testApplyService;

    // 원서 접수
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "원서 접수")
    public ResponseEntity<ApiResponseDTO> apply(
            @RequestPart("testId") String testId,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            Authentication authentication) {

        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        TestApplyDTO testApplyDTO = new TestApplyDTO();
        testApplyDTO.setTestId(Long.parseLong(testId));
        testApplyDTO.setUserId(userDTO.getId());

        Long id = testApplyService.apply(testApplyDTO, files);
        return ResponseEntity.ok(ApiResponseDTO.of(true, "원서 접수가 완료되었습니다.", id));
    }

    // 접수 취소
    @DeleteMapping("/{id}")
    @Operation(summary = "접수 취소")
    public ResponseEntity<ApiResponseDTO> cancel(
            @PathVariable Long id,
            Authentication authentication) {

        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        testApplyService.cancel(id, userDTO.getId());
        return ResponseEntity.ok(ApiResponseDTO.of(true, "접수가 취소되었습니다."));
    }

    // 내 합격 여부 조회
    @GetMapping("/my-results")
    @Operation(summary = "내 합격 여부 조회")
    public ResponseEntity<ApiResponseDTO> getMyResults(Authentication authentication) {

        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        List<MyTestResultDTO> list = testApplyService.getMyResults(userDTO.getId());
        return ResponseEntity.ok(ApiResponseDTO.of(true, "조회 성공", list));
    }

    // 내 접수 목록 조회
    @GetMapping("/my")
    @Operation(summary = "내 접수 목록 조회")
    public ResponseEntity<ApiResponseDTO> getMyApplyList(Authentication authentication) {

        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        List<TestApplyDTO> list = testApplyService.getMyApplyList(userDTO.getId());
        return ResponseEntity.ok(ApiResponseDTO.of(true, "조회 성공", list));
    }
}
