package com.app.springapp.domain.dto.response;

import com.app.springapp.domain.enums.SocialProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import com.app.springapp.domain.dto.SocialUserDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "소셜 유저 응답 DTO")
public class SocialUserResponseDTO {
    @Schema(description = "소셜 유저 번호", example = "1")
    private Long id;
    @Schema(description = "소셜 제공자 ID", example = "1234567890")
    private String socialUserProviderId;
    @Schema(description = "소셜 제공자", example = "google")
    private SocialProvider socialUserProvider;
    @Schema(description = "유저 번호", example = "1")
    private Long userId;

    public static SocialUserResponseDTO from(SocialUserDTO dto) {
        SocialUserResponseDTO res = new SocialUserResponseDTO();
        res.setId(dto.getId());
        res.setSocialUserProviderId(dto.getSocialUserProviderId());
        res.setSocialUserProvider(dto.getSocialUserProvider());
        res.setUserId(dto.getUserId());
        return res;
    }
}
