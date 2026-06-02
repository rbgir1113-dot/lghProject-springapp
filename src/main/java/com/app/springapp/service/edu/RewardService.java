package com.app.springapp.service.edu;

public interface RewardService {

    // 조건에 맞는 EXP와 배지를 자동 지급
    public int grantReward(Long userId, String rewardType, String rewardCondition, Long rewardReferenceId);


}
