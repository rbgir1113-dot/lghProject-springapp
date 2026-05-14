package com.app.springapp.service;

import com.app.springapp.domain.dto.request.CommentRequestDTO;
import com.app.springapp.domain.dto.response.CommentResponseDTO;

import java.util.List;

public interface CommentService {
    public List<CommentResponseDTO> getAllPostComments(Long postId);

    public void writePostComment(Long postId, CommentRequestDTO commentRequestDTO);

    public void writePostReply(Long postId, Long commentId, CommentRequestDTO commentRequestDTO);

    public void updateComment(Long commentId, CommentRequestDTO commentRequestDTO);

    public void deleteComment(Long commentId);
}
