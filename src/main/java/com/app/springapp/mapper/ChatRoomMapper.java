package com.app.springapp.mapper;

import com.app.springapp.domain.dto.ChatRoomDTO;
import com.app.springapp.domain.vo.ChatRoomVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChatRoomMapper {
//    채팅방 방 목록 불러와주기
    public List<ChatRoomDTO> selectAll();

//    채팅방 목록 페이징 조회
    public List<ChatRoomDTO> selectAllWithPaging(Map<String, Object> filters);

//    채팅방 전체 개수 조회
    public int selectCount();

//    채팅방 생성
    public void insert(ChatRoomVO chatRoomVO);

//    채팅방 수정
}
