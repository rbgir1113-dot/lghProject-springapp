package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.MyPageCertificateApplyCompleteResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateCourseResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateDetailResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateItemResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateSummaryResponseDTO;
import com.app.springapp.domain.vo.CertReceiveAddressVO;
import com.app.springapp.domain.vo.CertRenewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyPageCertificateMapper {

    // 마이페이지 자격증 목록 조회
    List<MyPageCertificateItemResponseDTO> selectCertificateListByUserId(Long userId);

    // 선택한 자격증 상세 조회
    MyPageCertificateDetailResponseDTO selectCertificateDetailByTestResultId(
            @Param("testResultId") Long testResultId,
            @Param("userId") Long userId
    );

    // 마이페이지 자격증 요약 조회
    MyPageCertificateSummaryResponseDTO selectCertificateSummaryByUserId(Long userId);

    // 마이페이지 자격증 수강중인 강좌 조회
    List<MyPageCertificateCourseResponseDTO> selectCourseListByUserId(Long userId);

    // 자격증 실물 신청 저장
    void insertCertRenew(CertRenewVO certRenewVO);

    // 자격증 실물 수령 주소 저장
    void insertCertReceiveAddress(CertReceiveAddressVO certReceiveAddressVO);

    // 자격증 실물 신청 완료 조회
    MyPageCertificateApplyCompleteResponseDTO selectApplyCompleteByCertRenewId(
            @Param("certRenewId") Long certRenewId,
            @Param("userId") Long userId
    );
}