package com.app.springapp.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SocialProvider {
    LOCAL, GOOGLE, KAKAO, NAVER;

    @JsonValue
    public String getValue() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static SocialProvider fromValue(String value) {
        if (value == null) return LOCAL;
        return switch (value.toLowerCase()) {
            case "google" -> GOOGLE;
            case "kakao"  -> KAKAO;
            case "naver"  -> NAVER;
            default       -> LOCAL;
        };
    }
}
