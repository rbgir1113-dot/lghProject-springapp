package com.app.springapp.service;

import com.app.springapp.domain.dto.response.AddressSearchResponseDTO;

import java.util.List;

public interface AddressSearchService {

    // 도로명주소 검색
    List<AddressSearchResponseDTO> searchAddress(String keyword);
}