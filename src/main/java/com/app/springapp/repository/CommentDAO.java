package com.app.springapp.repository;

import com.app.springapp.domain.dto.CommentDTO;
import com.app.springapp.domain.vo.CommentVO;
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

    public void save(CommentVO commentVO){
        commentMapper.insert(commentVO);
    }

    public int existByIdAndPostId(CommentVO commentVO) {
        return commentMapper.existByIdAndPostId(commentVO);
    }

    public int existByIdAndUserId(CommentVO commentVO) {
        return commentMapper.existByIdAndUserId(commentVO);
    }

    public void update(CommentVO commentVO) {
        commentMapper.update(commentVO);
    }

    public void updateIsDeleted(CommentVO commentVO) {
        commentMapper.updateIsDeleted(commentVO);
    }

    public void updateRepliesIsDeleted(Long commentId) {
        commentMapper.updateRepliesIsDeleted(commentId);
    }

    public int isParentComment(Long commentId) {
        return commentMapper.isParentComment(commentId);
    }
}
