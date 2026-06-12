package com.app.springapp.domain.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class SignWordVO {
    private Long id;
    private String signWordTitle;
    private String signWordDescription;
    private String signWordCategory;
    private String signWordThumbnailUrl;
    private String signWordVideoUrl;
    private String signWordImages;
    private String signWordSourceUrl;
    private LocalDateTime signWordCreateAt;
    private LocalDateTime signWordUpdateAt;
    private String signWordEmoji;

}