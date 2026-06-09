package com.app.springapp.service;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.CommunityUserResponseDTO;
import com.app.springapp.domain.dto.response.UserResponseDTO;

import java.util.Map;

public interface CommunityProfileService {
//    유저 정보 및 활동 결과 불러오기
    public CommunityUserResponseDTO getUserInfo(Map<String,Object> req);
}
