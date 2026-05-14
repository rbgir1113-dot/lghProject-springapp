package com.app.springapp.service;

import com.app.springapp.domain.dto.response.CommentResponseDTO;

import java.util.List;

public interface CommentService {
//    게시글 상세 페이지 에서 해당 게시글에 달린 댓글 조회
    List<CommentResponseDTO> getAllPostComments(Long postId);

//    특정 유저가 남긴 모든 댓글 조회 (인기순 CommentDTO 의 commentLikeCount 기준으로, 최신순, CommentDTO 의 ID 기준으로)

//    댓글 작성

//    대댓글 작성

//    자신이 작성 한 댓글 수정

//    자신이 작성 한 댓글 삭제 (댓글 삭제 시 대댓글도 모두 지워지도록 하기)

//    자신이 작성 한 대댓글 삭제
}
