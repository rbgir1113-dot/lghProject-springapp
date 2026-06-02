package com.app.springapp.domain.dto;

import com.app.springapp.domain.enums.SocialProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "소셜 유저 DTO")
public class SocialUserDTO {
    @Schema(description = "소셜 유저 번호", example = "1")
    private Long id;
    @Schema(description = "소셜 유저 제공자 ID", example = "1234567890")
    private String socialUserProviderId;
    @Schema(description = "소셜 유저 제공자", example = "google")
    private SocialProvider socialUserProvider;
    @Schema(description = "유저 번호", example = "1")
    private Long userId;
}
