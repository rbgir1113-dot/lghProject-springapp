package com.app.springapp.domain.dto.request;

import com.app.springapp.domain.enums.SocialProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "소셜 유저 요청 DTO")
public class SocialUserRequestDTO {
    @Schema(description = "소셜 제공자 ID", example = "1234567890", required = true)
    private String socialUserProviderId;
    @Schema(description = "소셜 제공자", example = "google", required = true)
    private SocialProvider socialUserProvider;
    @Schema(description = "유저 번호", example = "1", required = true)
    private Long userId;
}
