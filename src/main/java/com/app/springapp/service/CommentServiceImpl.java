package com.app.springapp.service;

import com.app.springapp.domain.dto.request.CommentRequestDTO;
import com.app.springapp.domain.dto.response.CommentResponseDTO;
import com.app.springapp.domain.vo.CommentVO;
import com.app.springapp.exception.CommentException;
import com.app.springapp.repository.CommentDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentDAO commentDAO;
    private final CommunityAuthService communityAuthService;

    //    특정 게시글 상세 페이지 내에서 해당 게시글에 달린 댓글 불러오기
    @Override
    public List<CommentResponseDTO> getAllPostComments(Long postId) {
        return commentDAO.findAllByPostId(postId)
                .stream()
                .map(CommentResponseDTO::from)
                .collect(Collectors.toList());
    }

//    게시글 에 댓글 작성
    @Override
    public void writePostComment(Long postId, CommentRequestDTO commentRequestDTO) {
        CommentVO commentVO = CommentVO.from(commentRequestDTO);
        commentVO.setPostId(postId);
        commentVO.setUserId(communityAuthService.getUserId());

//        유저 토큰 검증 시나리오
        communityAuthService.checkUserValidity(postId);

        try {
            commentDAO.save(commentVO);
        } catch (Exception e) {
            throw new CommentException(HttpStatus.BAD_REQUEST, "댓글 작성 실패");
        }
    }
}
