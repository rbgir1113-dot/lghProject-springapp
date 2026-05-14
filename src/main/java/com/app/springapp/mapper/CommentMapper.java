package com.app.springapp.mapper;

import com.app.springapp.domain.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    // 댓글 조회 (게시글에 달린 댓글 조회)
    public List<CommentDTO> selectAllByPostId(Long postId);

    // 댓글 작성

    // 댓글 수정

    // 댓글 삭제 (소프트 삭제)
}
