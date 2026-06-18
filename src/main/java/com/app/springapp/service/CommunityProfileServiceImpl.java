package com.app.springapp.service;

import com.app.springapp.domain.dto.CommunityUserDTO;
import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.CommunityUserResponseDTO;
import com.app.springapp.domain.dto.response.UserResponseDTO;
import com.app.springapp.exception.UserException;
import com.app.springapp.repository.CommunityUserDAO;
import com.app.springapp.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = {Exception.class, UserException.class})
@RequiredArgsConstructor
public class CommunityProfileServiceImpl implements CommunityProfileService {

    private final CommunityUserDAO communityUserDAO;

    private static final int MAX_LEVEL = 100;

    //    커뮤니티 프로필 페이지에서 쓸 유저 정보 추출
    @Override
    public CommunityUserResponseDTO getUserInfo(Map<String,Object> req) {
        CommunityUserDTO communityUserDTO = communityUserDAO.findById(req).orElseThrow(
            () -> {throw new UserException("해당 유저 정보를 불러올 수 없습니다", HttpStatus.NOT_FOUND);
        });
        CommunityUserResponseDTO dto = CommunityUserResponseDTO.from(communityUserDTO);
        dto.setUserLevel(calculateLevel(communityUserDTO.getUserExp()));
        dto.setUserLevelName(getLevelName(dto.getUserLevel()));
        return dto;
    }

//    커뮤니티 내에서 4개의 최신 유저 프로필 보여주기
    @Override
    public List<CommunityUserResponseDTO> getUsers(Map<String, Object> req) {
        return communityUserDAO.findAll(req)
                .stream()
                .map(dto -> {
                    CommunityUserResponseDTO resp = CommunityUserResponseDTO.from(dto);
                    resp.setUserLevel(calculateLevel(dto.getUserExp()));
                    resp.setUserLevelName(getLevelName(resp.getUserLevel()));
                    return resp;
                })
                .collect(Collectors.toList());
    }

    private int calculateLevel(long totalExp) {
        int level = 1;
        long remaining = totalExp;
        while (level < MAX_LEVEL) {
            long required = 100L + ((long) level - 1L) * 20L;
            if (remaining < required) break;
            remaining -= required;
            level++;
        }
        return level;
    }

    private String getLevelName(int level) {
        if (level >= 100) return "이음";
        if (level >= 90) return "수어 마스터";
        if (level >= 80) return "연결자";
        if (level >= 70) return "숙련가";
        if (level >= 60) return "공감가";
        if (level >= 50) return "표현가";
        if (level >= 40) return "소통가";
        if (level >= 30) return "실천가";
        if (level >= 20) return "학습자";
        if (level >= 10) return "새싹 수어인";
        return "입문자";
    }
}
