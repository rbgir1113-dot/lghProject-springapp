package com.app.springapp.mapper;

import com.app.springapp.domain.dto.CommentDTO;
import com.app.springapp.domain.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    public List<CommentDTO> selectAllByPostId(Long postId);

    public void insert(CommentVO commentVO);

    public int existByIdAndPostId(CommentVO commentVO);

    public int existByIdAndUserId(CommentVO commentVO);

    public void update(CommentVO commentVO);

    public void updateIsDeleted(CommentVO commentVO);

    public void updateRepliesIsDeleted(Long commentId);

    public int isParentComment(Long commentId);
}
