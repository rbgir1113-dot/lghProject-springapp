package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "도로명주소 검색 응답 DTO")
public class AddressSearchResponseDTO {

    @Schema(description = "우편번호", example = "06236")
    private String zipNo;

    @Schema(description = "전체 도로명 주소", example = "서울특별시 강남구 테헤란로 123")
    private String roadAddr;

    @Schema(description = "도로명 주소 기본", example = "서울특별시 강남구 테헤란로 123")
    private String roadAddrPart1;

    @Schema(description = "도로명 주소 참고항목", example = "(역삼동)")
    private String roadAddrPart2;

    @Schema(description = "지번 주소", example = "서울특별시 강남구 역삼동 123-45")
    private String jibunAddr;

    @Schema(description = "건물명", example = "이음빌딩")
    private String bdNm;
}