package com.app.springapp.service;

import com.app.springapp.domain.dto.TestApplyDTO;
import java.util.List;

public interface TestApplyService {
    // 원서 접수 (정원 초과 시 예외 발생)
    void apply(TestApplyDTO testApplyDTO);

    // 내 접수 목록 조회
    List<TestApplyDTO> getMyApplyList(Long userId);
}
