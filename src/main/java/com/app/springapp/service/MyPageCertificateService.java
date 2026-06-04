package com.app.springapp.service;

import com.app.springapp.domain.dto.request.MyPageCertificateApplyRequestDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateApplyCompleteResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateDetailResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateResponseDTO;

public interface MyPageCertificateService {

    // 마이페이지 자격증 페이지 조회
    MyPageCertificateResponseDTO getCertificatePage(Long userId);

    // 선택한 자격증 상세 조회
    MyPageCertificateDetailResponseDTO getCertificateDetail(Long testResultId, Long userId);

    // 자격증 실물 신청
    MyPageCertificateApplyCompleteResponseDTO applyCertificate(MyPageCertificateApplyRequestDTO requestDTO, Long userId);

    // 자격증 실물 신청 완료 조회
    MyPageCertificateApplyCompleteResponseDTO getApplyComplete(Long certRenewId, Long userId);
}