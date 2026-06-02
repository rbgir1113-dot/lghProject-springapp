package com.app.springapp.repository;

import com.app.springapp.domain.dto.UserBadgeDTO;
import com.app.springapp.domain.vo.UserBadgeVO;
import com.app.springapp.mapper.RewardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RewardDAO {
    private final RewardMapper rewardMapper;

    // 사용자 EXP 증가
    public void addUserExp(Long userId, int rewardExp) {
        rewardMapper.updateUserExp(userId, rewardExp);
    }

    // 사용자 누적 EXP 조회
    public int findUserExp(Long userId) {
        return rewardMapper.selectUserExp(userId);
    }

    // 사용자 배지 보유 여부 조회
    public boolean existsUserBadge(Long userId, Long badgeId) {
        return rewardMapper.countUserBadge(userId, badgeId) > 0;
    }

    // 사용자 배지 지급
    public void saveUserBadge(UserBadgeVO userBadgeVO) {
        rewardMapper.insertUserBadge(userBadgeVO);
    }

    // 사용자 배지 개수 조회
    public int findUserBadgeCount(Long userId) {
        return rewardMapper.countUserBadgesByUserId(userId);
    }

    // 사용자 배지 목록 조회
    public List<UserBadgeDTO> findUserBadges(Long userId) {
        return rewardMapper.selectUserBadgesByUserId(userId);
    }
}
