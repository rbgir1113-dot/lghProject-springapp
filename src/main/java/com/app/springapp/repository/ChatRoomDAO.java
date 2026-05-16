package com.app.springapp.repository;

import com.app.springapp.domain.dto.ChatRoomDTO;
import com.app.springapp.domain.vo.ChatRoomVO;
import com.app.springapp.mapper.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ChatRoomDAO {

    private final ChatRoomMapper chatRoomMapper;

//    채팅방 목록 전체 불러와주기
    public List<ChatRoomDTO> findAll(){
        return chatRoomMapper.selectAll();
    }

//    채팅방 목록 페이징 조회
    public List<ChatRoomDTO> findAllWithPaging(Map<String, Object> filters){
        return chatRoomMapper.selectAllWithPaging(filters);
    }

//    채팅방 전체 개수 조회
    public int findCount(){
        return chatRoomMapper.selectCount();
    }

//    채팅방 생성
    public void save(ChatRoomVO chatRoomVO){
        chatRoomMapper.insert(chatRoomVO);
    }
}
