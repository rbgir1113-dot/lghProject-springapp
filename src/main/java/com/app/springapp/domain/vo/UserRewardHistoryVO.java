package com.app.springapp.domain.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class UserRewardHistoryVO {
    private Long id;
    private Long userId;
    private Long rewardPolicyId;
    private Long rewardReferenceId;
    private int userRewardExp;
    private LocalDateTime userRewardCreateAt;
}
