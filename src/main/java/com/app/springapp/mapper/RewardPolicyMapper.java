package com.app.springapp.mapper;

import com.app.springapp.domain.vo.RewardPolicyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RewardPolicyMapper {

    // 보상 번호로 정책 조회
    public RewardPolicyVO selectById(Long id);

    // 보상 조건으로 정책 조회
    public RewardPolicyVO selectByTypeAndCondition(String rewardType, String rewardCondition);

    // 보상 종류별 정책 목록 조회
    public List<RewardPolicyVO> selectByType(String rewardType);
}
