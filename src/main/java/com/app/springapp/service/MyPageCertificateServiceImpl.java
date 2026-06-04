package com.app.springapp.service;

import com.app.springapp.domain.dto.request.MyPageCertificateApplyRequestDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateApplyCompleteResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateDetailResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateSummaryResponseDTO;
import com.app.springapp.domain.vo.CertReceiveAddressVO;
import com.app.springapp.domain.vo.CertRenewVO;
import com.app.springapp.exception.MyPageException;
import com.app.springapp.repository.MyPageCertificateDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MyPageCertificateServiceImpl implements MyPageCertificateService {

    private static final Logger log = LoggerFactory.getLogger(MyPageCertificateServiceImpl.class);

    private final MyPageCertificateDAO myPageCertificateDAO;

    // 마이페이지 자격증 페이지 조회
    @Override
    @Transactional(readOnly = true)
    public MyPageCertificateResponseDTO getCertificatePage(Long userId) {
        MyPageCertificateResponseDTO responseDTO = new MyPageCertificateResponseDTO();

        responseDTO.setCertificateList(myPageCertificateDAO.findCertificateListByUserId(userId));
        responseDTO.setSummary(getCertificateSummary(userId));
        responseDTO.setCourseList(myPageCertificateDAO.findCourseListByUserId(userId));

        return responseDTO;
    }

    // 선택한 자격증 상세 조회
    @Override
    @Transactional(readOnly = true)
    public MyPageCertificateDetailResponseDTO getCertificateDetail(Long testResultId, Long userId) {
        return myPageCertificateDAO.findCertificateDetailByTestResultId(testResultId, userId)
                .orElseThrow(() -> new MyPageException("신청 가능한 자격증 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    // 자격증 실물 신청
    @Override
    public MyPageCertificateApplyCompleteResponseDTO applyCertificate(MyPageCertificateApplyRequestDTO requestDTO, Long userId) {
        MyPageCertificateDetailResponseDTO certificateDetail = getCertificateDetail(requestDTO.getTestResultId(), userId);

        log.info("신청 요청 testResultId: {}", requestDTO.getTestResultId());
        log.info("신청 요청 userId: {}", userId);
        log.info("신청 전 상세 조회 결과: {}", certificateDetail);
        log.info("신청 상태: {}", certificateDetail.getCertApplyStatus());
        log.info("신청 가능 여부: {}", certificateDetail.isCanApply());

        if (!"none".equals(certificateDetail.getCertApplyStatus())) {
            throw new MyPageException("이미 실물 신청이 진행 중인 자격증입니다.", HttpStatus.BAD_REQUEST);
        }

        CertRenewVO certRenewVO = createCertRenewVO(requestDTO, userId);
        myPageCertificateDAO.saveCertRenew(certRenewVO);

        CertReceiveAddressVO certReceiveAddressVO = createCertReceiveAddressVO(requestDTO, certRenewVO.getId());
        myPageCertificateDAO.saveCertReceiveAddress(certReceiveAddressVO);

        return getApplyComplete(certRenewVO.getId(), userId);
    }

    // 자격증 실물 신청 완료 조회
    @Override
    @Transactional(readOnly = true)
    public MyPageCertificateApplyCompleteResponseDTO getApplyComplete(Long certRenewId, Long userId) {
        return myPageCertificateDAO.findApplyCompleteByCertRenewId(certRenewId, userId)
                .orElseThrow(() -> new MyPageException("자격증 실물 신청 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    // 마이페이지 자격증 요약 조회
    private MyPageCertificateSummaryResponseDTO getCertificateSummary(Long userId) {
        return myPageCertificateDAO.findCertificateSummaryByUserId(userId)
                .orElseGet(MyPageCertificateSummaryResponseDTO::new);
    }

    // 자격증 실물 신청 VO 생성
    private CertRenewVO createCertRenewVO(MyPageCertificateApplyRequestDTO requestDTO, Long userId) {
        CertRenewVO certRenewVO = new CertRenewVO();

        certRenewVO.setTestResultId(requestDTO.getTestResultId());
        certRenewVO.setUserId(userId);
        certRenewVO.setCertRenewType("issue");
        certRenewVO.setCertReceiveType(requestDTO.getCertReceiveType());
        certRenewVO.setCertNo(createCertNo(requestDTO.getTestResultId(), userId));
        certRenewVO.setCertName(requestDTO.getCertName());
        certRenewVO.setCertPhone(requestDTO.getCertPhone());
        certRenewVO.setCertAddress(requestDTO.getCertAddress());

        return certRenewVO;
    }

    // 자격증 수령 주소 VO 생성
    private CertReceiveAddressVO createCertReceiveAddressVO(MyPageCertificateApplyRequestDTO requestDTO, Long certRenewId) {
        CertReceiveAddressVO certReceiveAddressVO = new CertReceiveAddressVO();

        certReceiveAddressVO.setCertPostcode(requestDTO.getCertPostcode());
        certReceiveAddressVO.setCertRoadAddress(requestDTO.getCertRoadAddress());
        certReceiveAddressVO.setCertJibunAddress(requestDTO.getCertJibunAddress());
        certReceiveAddressVO.setCertDetailAddress(requestDTO.getCertDetailAddress());
        certReceiveAddressVO.setCertAddress(requestDTO.getCertAddress());
        certReceiveAddressVO.setCertRenewId(certRenewId);

        return certReceiveAddressVO;
    }

    // 자격증 번호 생성
    private String createCertNo(Long testResultId, Long userId) {
        return "CERT-" + userId + "-" + testResultId;
    }
}