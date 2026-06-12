package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@Schema(description = "주간 추천 수어 이모지 응답 DTO")
public class SignWordWeeklyRecommendationResponseDTO {

    @Schema(description = "수어 단어 번호", example = "1")
    private Long id;

    @Schema(description = "수어 단어 제목", example = "기쁨")
    private String signWordTitle;

    @Schema(description = "추천 이모지", example = "😊")
    private String signWordEmoji;

}

