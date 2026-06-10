package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "팔로우 요청 DTO")
public class FollowRequestDTO {
//    @Schema(description = "팔로우 타입", example = "일반 팔로우", required = true)
//    private String followType;
//    @Schema(description = "팔로우 타입 번호", example = "1", required = true)
//    private Long followTypeId;
    @Schema(description = "팔로워 유저 번호", example = "1", required = true)
    private Long followerId;
    @Schema(description = "팔로잉 유저 번호", example = "2", required = true)
    private Long followingId;
}
