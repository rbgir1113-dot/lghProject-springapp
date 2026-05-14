package com.app.springapp.repository;

import com.app.springapp.domain.dto.CommentDTO;
import com.app.springapp.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDAO {
    private final CommentMapper commentMapper;

    public List<CommentDTO> findAllByPostId(Long postId){
        return commentMapper.selectAllByPostId(postId);
    }
}
