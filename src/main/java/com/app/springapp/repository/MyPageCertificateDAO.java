package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.MyPageCertificateApplyCompleteResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateCourseResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateDetailResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateItemResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateSummaryResponseDTO;
import com.app.springapp.domain.vo.CertReceiveAddressVO;
import com.app.springapp.domain.vo.CertRenewVO;
import com.app.springapp.mapper.MyPageCertificateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyPageCertificateDAO {

    private final MyPageCertificateMapper myPageCertificateMapper;

    // 마이페이지 자격증 목록 조회
    public List<MyPageCertificateItemResponseDTO> findCertificateListByUserId(Long userId) {
        return myPageCertificateMapper.selectCertificateListByUserId(userId);
    }

    // 선택한 자격증 상세 조회
    public Optional<MyPageCertificateDetailResponseDTO> findCertificateDetailByTestResultId(Long testResultId, Long userId) {
        return Optional.ofNullable(myPageCertificateMapper.selectCertificateDetailByTestResultId(testResultId, userId));
    }

    // 마이페이지 자격증 요약 조회
    public Optional<MyPageCertificateSummaryResponseDTO> findCertificateSummaryByUserId(Long userId) {
        return Optional.ofNullable(myPageCertificateMapper.selectCertificateSummaryByUserId(userId));
    }

    // 마이페이지 자격증 수강중인 강좌 조회
    public List<MyPageCertificateCourseResponseDTO> findCourseListByUserId(Long userId) {
        return myPageCertificateMapper.selectCourseListByUserId(userId);
    }

    // 자격증 실물 신청 저장
    public void saveCertRenew(CertRenewVO certRenewVO) {
        myPageCertificateMapper.insertCertRenew(certRenewVO);
    }

    // 자격증 실물 수령 주소 저장
    public void saveCertReceiveAddress(CertReceiveAddressVO certReceiveAddressVO) {
        myPageCertificateMapper.insertCertReceiveAddress(certReceiveAddressVO);
    }

    // 자격증 실물 신청 완료 조회
    public Optional<MyPageCertificateApplyCompleteResponseDTO> findApplyCompleteByCertRenewId(Long certRenewId, Long userId) {
        return Optional.ofNullable(myPageCertificateMapper.selectApplyCompleteByCertRenewId(certRenewId, userId));
    }
}