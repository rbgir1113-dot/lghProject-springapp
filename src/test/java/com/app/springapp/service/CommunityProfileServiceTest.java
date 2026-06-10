package com.app.springapp.service;

import com.app.springapp.domain.dto.response.CommunityUserResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class CommunityProfileServiceTest {
    @Autowired
    private CommunityProfileService communityProfileService;

    @Test
    public void getUserInfoTest() {
        Long id = 1L;
        Long followerId = 1L;

        Map<String,Object> req = new HashMap<>();
        req.put("id", id);
        req.put("userId", followerId);

        CommunityUserResponseDTO communityUserResponseDTO = communityProfileService.getUserInfo(req);
        log.info("userResponseDTO={}", communityUserResponseDTO);
    }
}
