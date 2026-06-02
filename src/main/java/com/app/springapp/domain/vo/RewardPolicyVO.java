package com.app.springapp.domain.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class RewardPolicyVO {
    private Long id;
    private String rewardType;
    private String rewardCondition;
    private int rewardExp;
    private Long badgeId;
}