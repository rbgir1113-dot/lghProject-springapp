package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.request.MyPageCertificateApplyRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.AddressSearchService;
import com.app.springapp.service.MyPageCertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/api/mypage/certificates")
public class MyPageCertificateApi {

    private final MyPageCertificateService myPageCertificateService;
    private final AddressSearchService addressSearchService;

    // 마이페이지 자격증 페이지 조회
    @GetMapping("")
    @Operation(summary = "마이페이지 자격증 페이지 조회", description = "로그인한 회원의 합격 자격증, 신청 상태, 수강중인 강좌 요약을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "마이페이지 자격증 페이지 조회 성공")
    public ResponseEntity<ApiResponseDTO> getCertificatePage(Authentication authentication) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        return ResponseEntity.ok(
                ApiResponseDTO.of(
                        true,
                        "마이페이지 자격증 페이지 조회 성공",
                        myPageCertificateService.getCertificatePage(userDTO.getId())
                )
        );
    }

    // 선택한 자격증 상세 조회
    @GetMapping("/{testResultId}")
    @Operation(summary = "선택한 자격증 상세 조회", description = "선택한 합격 자격증의 실물 신청 상태와 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "선택한 자격증 상세 조회 성공")
    public ResponseEntity<ApiResponseDTO> getCertificateDetail(
            @PathVariable Long testResultId,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        return ResponseEntity.ok(
                ApiResponseDTO.of(
                        true,
                        "선택한 자격증 상세 조회 성공",
                        myPageCertificateService.getCertificateDetail(testResultId, userDTO.getId())
                )
        );
    }

    // 자격증 실물 신청
    @PostMapping("/apply")
    @Operation(summary = "자격증 실물 신청", description = "합격한 자격증의 실물 발급을 신청하고 수령 주소를 저장합니다.")
    @ApiResponse(responseCode = "200", description = "자격증 실물 신청 성공")
    public ResponseEntity<ApiResponseDTO> applyCertificate(
            @RequestBody MyPageCertificateApplyRequestDTO requestDTO,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        return ResponseEntity.ok(
                ApiResponseDTO.of(
                        true,
                        "자격증 실물 신청 성공",
                        myPageCertificateService.applyCertificate(requestDTO, userDTO.getId())
                )
        );
    }

    // 자격증 실물 신청 완료 조회
    @GetMapping("/apply/{certRenewId}")
    @Operation(summary = "자격증 실물 신청 완료 조회", description = "자격증 실물 신청 완료 화면에 필요한 신청 정보와 수령 주소를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "자격증 실물 신청 완료 조회 성공")
    public ResponseEntity<ApiResponseDTO> getApplyComplete(
            @PathVariable Long certRenewId,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        return ResponseEntity.ok(
                ApiResponseDTO.of(
                        true,
                        "자격증 실물 신청 완료 조회 성공",
                        myPageCertificateService.getApplyComplete(certRenewId, userDTO.getId())
                )
        );
    }

    // 도로명주소 검색
    @GetMapping("/address-search")
    @Operation(summary = "도로명주소 검색", description = "도로명주소 검색 API를 이용해 우편번호, 도로명주소, 지번주소 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "도로명주소 검색 성공")
    public ResponseEntity<ApiResponseDTO> searchAddress(@RequestParam String keyword) {
        return ResponseEntity.ok(
                ApiResponseDTO.of(
                        true,
                        "도로명주소 검색 성공",
                        addressSearchService.searchAddress(keyword)
                )
        );
    }
}