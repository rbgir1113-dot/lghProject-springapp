package com.app.springapp.service;

import com.app.springapp.domain.dto.response.AddressSearchResponseDTO;
import com.app.springapp.exception.MyPageException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressSearchServiceImpl implements AddressSearchService {

    private final RestTemplate restTemplate;

    @Value("${juso.api.base-url}")
    private String jusoApiBaseUrl;

    @Value("${juso.api.confm-key}")
    private String jusoApiConfmKey;

    // 도로명주소 검색
    @Override
    public List<AddressSearchResponseDTO> searchAddress(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new MyPageException("주소 검색어를 입력해주세요.", HttpStatus.BAD_REQUEST);
        }

        URI requestUri = UriComponentsBuilder.fromHttpUrl(jusoApiBaseUrl)
                .queryParam("currentPage", 1)
                .queryParam("countPerPage", 10)
                .queryParam("keyword", keyword.trim())
                .queryParam("confmKey", jusoApiConfmKey)
                .queryParam("resultType", "json")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        LinkedHashMap response = restTemplate.getForObject(requestUri, LinkedHashMap.class);

        return convertAddressList(response);
    }

    // 도로명주소 API 응답을 화면 응답 DTO로 변환
    private List<AddressSearchResponseDTO> convertAddressList(LinkedHashMap response) {
        if (response == null || response.get("results") == null) {
            throw new MyPageException("주소 검색 결과를 불러오지 못했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LinkedHashMap results = (LinkedHashMap) response.get("results");
        LinkedHashMap common = (LinkedHashMap) results.get("common");

        String errorCode = String.valueOf(common.get("errorCode"));
        String errorMessage = String.valueOf(common.get("errorMessage"));

        if (!"0".equals(errorCode)) {
            throw new MyPageException("주소 검색 오류: " + errorMessage, HttpStatus.BAD_REQUEST);
        }

        Object jusoObject = results.get("juso");

        if (!(jusoObject instanceof List<?> jusoList)) {
            return new ArrayList<>();
        }

        List<AddressSearchResponseDTO> addressList = new ArrayList<>();

        for (Object item : jusoList) {
            LinkedHashMap juso = (LinkedHashMap) item;
            AddressSearchResponseDTO responseDTO = new AddressSearchResponseDTO();

            responseDTO.setZipNo(getStringValue(juso, "zipNo"));
            responseDTO.setRoadAddr(getStringValue(juso, "roadAddr"));
            responseDTO.setRoadAddrPart1(getStringValue(juso, "roadAddrPart1"));
            responseDTO.setRoadAddrPart2(getStringValue(juso, "roadAddrPart2"));
            responseDTO.setJibunAddr(getStringValue(juso, "jibunAddr"));
            responseDTO.setBdNm(getStringValue(juso, "bdNm"));

            addressList.add(responseDTO);
        }

        return addressList;
    }

    // 외부 API 응답 값을 문자열로 변환
    private String getStringValue(LinkedHashMap map, String key) {
        Object value = map.get(key);

        if (value == null) {
            return "";
        }

        return String.valueOf(value);
    }
}