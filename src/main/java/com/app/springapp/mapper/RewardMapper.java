package com.app.springapp.mapper;


import com.app.springapp.domain.dto.UserBadgeDTO;
import com.app.springapp.domain.vo.UserBadgeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RewardMapper {

    // 사용자 EXP 증가
    public void updateUserExp(Long userId, int rewardExp);

    // 사용자 누적 EXP 조회
    public int selectUserExp(Long userId);

    // 사용자 배지 중복 조회
    public int countUserBadge(Long userId, Long badgeId);

    // 사용자 배지 지급
    public void insertUserBadge(UserBadgeVO userBadgeVO);

    // 사용자 배지 개수 조회
    public int countUserBadgesByUserId(Long userId);

    // 사용자 배지 목록 조회
    public List<UserBadgeDTO> selectUserBadgesByUserId(Long userId);
}