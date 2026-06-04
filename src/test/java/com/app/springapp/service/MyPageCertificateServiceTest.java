package com.app.springapp.service;

import com.app.springapp.domain.dto.request.MyPageCertificateApplyRequestDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateApplyCompleteResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateDetailResponseDTO;
import com.app.springapp.domain.dto.response.MyPageCertificateResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.ai.openai.api-key=test-key",
        "spring.ai.openai.chat.api-key=test-key",
        "toss.secret-key=test-secret-key"
})
@Slf4j
public class MyPageCertificateServiceTest {

    @Autowired
    private MyPageCertificateService myPageCertificateService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 마이페이지 자격증 페이지 조회 테스트
    @Test
    public void getCertificatePageTest() {
        Long userId = 1L;

        MyPageCertificateResponseDTO responseDTO = myPageCertificateService.getCertificatePage(userId);

        log.info("마이페이지 자격증 페이지 조회 결과: {}", responseDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getCertificateList()).isNotNull();
        assertThat(responseDTO.getSummary()).isNotNull();
        assertThat(responseDTO.getCourseList()).isNotNull();
    }

    // 선택한 자격증 상세 조회 테스트
    @Test
    public void getCertificateDetailTest() {
        Long userId = 1L;
        Long testResultId = createCertificateApplyTestData(userId);

        MyPageCertificateDetailResponseDTO responseDTO = myPageCertificateService.getCertificateDetail(testResultId, userId);

        log.info("선택 자격증 상세 조회 결과: {}", responseDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getTestResultId()).isEqualTo(testResultId);
        assertThat(responseDTO.getTestTitle()).isNotNull();
        assertThat(responseDTO.isCanApply()).isTrue();
    }

    // 자격증 실물 신청 테스트
    @Test
    public void applyCertificateTest() {
        Long userId = 1L;
        Long testResultId = createCertificateApplyTestData(userId);

        MyPageCertificateApplyRequestDTO requestDTO = new MyPageCertificateApplyRequestDTO();
        requestDTO.setTestResultId(testResultId);
        requestDTO.setCertReceiveType("우편수령");
        requestDTO.setCertName("김민준");
        requestDTO.setCertPhone("010-1234-5678");
        requestDTO.setCertPostcode("06236");
        requestDTO.setCertRoadAddress("서울특별시 강남구 테헤란로 123");
        requestDTO.setCertJibunAddress("서울특별시 강남구 역삼동 123-45");
        requestDTO.setCertDetailAddress("101동 1203호");
        requestDTO.setCertAddress("서울특별시 강남구 테헤란로 123, 101동 1203호");

        MyPageCertificateApplyCompleteResponseDTO responseDTO = myPageCertificateService.applyCertificate(requestDTO, userId);

        log.info("자격증 실물 신청 결과: {}", responseDTO);

        Long certRenewCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM TBL_CERT_RENEW WHERE TEST_RESULT_ID = ? AND USER_ID = ?",
                Long.class,
                testResultId,
                userId
        );

        Long certReceiveAddressCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM TBL_CERT_RECEIVE_ADDRESS TCRA JOIN TBL_CERT_RENEW TCR ON TCRA.CERT_RENEW_ID = TCR.ID WHERE TCR.TEST_RESULT_ID = ? AND TCR.USER_ID = ?",
                Long.class,
                testResultId,
                userId
        );

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getCertRenewId()).isNotNull();
        assertThat(responseDTO.getTestResultId()).isEqualTo(testResultId);
        assertThat(certRenewCount).isGreaterThan(0);
        assertThat(certReceiveAddressCount).isGreaterThan(0);
    }

    // 자격증 실물 신청 완료 조회 테스트
    @Test
    public void getApplyCompleteTest() {
        Long userId = 1L;
        Long testResultId = createCertificateApplyTestData(userId);

        MyPageCertificateApplyRequestDTO requestDTO = new MyPageCertificateApplyRequestDTO();
        requestDTO.setTestResultId(testResultId);
        requestDTO.setCertReceiveType("우편수령");
        requestDTO.setCertName("김민준");
        requestDTO.setCertPhone("010-1234-5678");
        requestDTO.setCertPostcode("06236");
        requestDTO.setCertRoadAddress("서울특별시 강남구 테헤란로 123");
        requestDTO.setCertJibunAddress("서울특별시 강남구 역삼동 123-45");
        requestDTO.setCertDetailAddress("101동 1203호");
        requestDTO.setCertAddress("서울특별시 강남구 테헤란로 123, 101동 1203호");

        MyPageCertificateApplyCompleteResponseDTO applyResponseDTO = myPageCertificateService.applyCertificate(requestDTO, userId);
        MyPageCertificateApplyCompleteResponseDTO responseDTO = myPageCertificateService.getApplyComplete(applyResponseDTO.getCertRenewId(), userId);

        log.info("자격증 실물 신청 완료 조회 결과: {}", responseDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getCertRenewId()).isEqualTo(applyResponseDTO.getCertRenewId());
        assertThat(responseDTO.getTestResultId()).isEqualTo(testResultId);
        assertThat(responseDTO.getCertAddress()).isNotNull();
    }

    // 자격증 신청 테스트용 합격 시험 결과 생성
    private Long createCertificateApplyTestData(Long userId) {
        Long testId = jdbcTemplate.queryForObject("SELECT SEQ_TEST.NEXTVAL FROM DUAL", Long.class);
        Long testApplyId = jdbcTemplate.queryForObject("SELECT SEQ_TEST_APPLY.NEXTVAL FROM DUAL", Long.class);
        Long testResultId = jdbcTemplate.queryForObject("SELECT SEQ_TEST_RESULT.NEXTVAL FROM DUAL", Long.class);

        jdbcTemplate.update(
                "INSERT INTO TBL_TEST (ID, TEST_TITLE, TEST_DETAIL, TEST_DATE, TEST_LIMIT, TEST_LOCATION, TEST_PRICE, TEST_IS_DELETED) VALUES (?, ?, ?, SYSDATE, ?, ?, ?, ?)",
                testId,
                "마이페이지 자격증 신청 테스트",
                "마이페이지 자격증 신청 테스트용 시험입니다.",
                100,
                "서울특별시 강남구",
                0,
                0
        );

        jdbcTemplate.update(
                "INSERT INTO TBL_TEST_APPLY (ID, TEST_APPLY_AT, USER_ID, TEST_ID) VALUES (?, SYSDATE, ?, ?)",
                testApplyId,
                userId,
                testId
        );

        jdbcTemplate.update(
                "INSERT INTO TBL_TEST_RESULT (ID, TEST_RESULT_POINT, TEST_APPLY_ID) VALUES (?, ?, ?)",
                testResultId,
                90,
                testApplyId
        );

        return testResultId;
    }
}