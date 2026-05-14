package com.app.springapp.service;

import com.app.springapp.domain.dto.response.CommentResponseDTO;
import com.app.springapp.repository.CommentDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentDAO commentDAO;

//    특정 게시글 상세 페이지 내에서 해당 게시글에 달린 댓글 불러오기
    @Override
    public List<CommentResponseDTO> getAllPostComments(Long postId) {
        return commentDAO.findAllByPostId(postId)
                .stream()
                .map(CommentResponseDTO::from)
                .collect(Collectors.toList());
    }
}
