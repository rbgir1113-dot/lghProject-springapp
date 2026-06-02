package com.app.springapp.repository;

import com.app.springapp.domain.vo.RewardPolicyVO;
import com.app.springapp.mapper.RewardPolicyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RewardPolicyDAO {
    private final RewardPolicyMapper rewardPolicyMapper;

    // 보상 번호로 정책 조회
    public RewardPolicyVO findById(Long id) {
        return rewardPolicyMapper.selectById(id);
    }

    // 보상 조건으로 정책 조회
    public RewardPolicyVO findByTypeAndCondition(String rewardType, String rewardCondition) {
        return rewardPolicyMapper.selectByTypeAndCondition(rewardType, rewardCondition);
    }

    // 보상 종류별 정책 목록 조회
    public List<RewardPolicyVO> findByType(String rewardType) {
        return rewardPolicyMapper.selectByType(rewardType);
    }
}
