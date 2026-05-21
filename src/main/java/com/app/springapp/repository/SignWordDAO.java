package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.SignWordResponseDTO;
import com.app.springapp.domain.vo.SignWordVO;
import com.app.springapp.mapper.SignWordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SignWordDAO {
    private final SignWordMapper signWordMapper;

    // 수어 전체 조회
    public List<SignWordResponseDTO> findAll() {
        return signWordMapper.selectAll();
    }

    // 수어 검색 조회
    public List<SignWordResponseDTO> findByKeyword(String keyword) {
        return signWordMapper.selectByKeyword(keyword);
    }

    // 수어 상세 조회
    public Optional<SignWordResponseDTO> findById(Long id) {
        return Optional.ofNullable(signWordMapper.select(id));
    }

    // 수어 원본 URL 조회
    public Optional<SignWordResponseDTO> findBySourceUrl(String signWordSourceUrl) {
        return Optional.ofNullable(signWordMapper.selectBySourceUrl(signWordSourceUrl));
    }

    // 수어 등록
    public void save(SignWordVO signWordVO) {
        signWordMapper.insert(signWordVO);
    }

    // 수어 수정
    public void update(SignWordVO signWordVO) {
        signWordMapper.update(signWordVO);
    }

    // 수어 삭제
    public void delete(Long id) {
        signWordMapper.delete(id);
    }

    // 수어 전체 개수 조회
    public int countAll() {
        return signWordMapper.countAll();
    }

    // 수어 검색 개수 조회
    public int countByKeyword(String keyword) {
        return signWordMapper.countByKeyword(keyword);
    }
}
