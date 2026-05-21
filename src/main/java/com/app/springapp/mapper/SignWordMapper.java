package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.SignWordResponseDTO;
import com.app.springapp.domain.vo.SignWordVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SignWordMapper {
    // 수어 전체 조회
    public List<SignWordResponseDTO> selectAll();

    // 수어 검색 조회
    public List<SignWordResponseDTO> selectByKeyword(String keyword);

    // 수어 상세 조회
    public SignWordResponseDTO select(Long id);

    // 수어 원본 URL 조회
    public SignWordResponseDTO selectBySourceUrl(String signWordSourceUrl);

    // 수어 등록
    public void insert(SignWordVO signWordVO);

    // 수어 수정
    public void update(SignWordVO signWordVO);

    // 수어 삭제
    public void delete(Long id);

    // 수어 전체 개수 조회
    public int countAll();

    // 수어 검색 개수 조회
    public int countByKeyword(String keyword);
}
