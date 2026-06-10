package com.app.springapp.domain.vo;

import com.app.springapp.domain.dto.request.FollowRequestDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
public class FollowVO {
    private Long id;
    private LocalDateTime followCreateAt;
    private String followType;
    private Long followTypeId;
    private Long followerId;
    private Long followingId;

    public static FollowVO from(FollowRequestDTO followRequestDTO) {
        FollowVO followVO = new FollowVO();
        followVO.setFollowerId(followRequestDTO.getFollowerId());
        followVO.setFollowingId(followRequestDTO.getFollowingId());

        return followVO;
    }
}
