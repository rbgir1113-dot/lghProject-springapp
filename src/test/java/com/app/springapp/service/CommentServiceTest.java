package com.app.springapp.service;

import com.app.springapp.domain.dto.response.CommentResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;

//    게시글에 달린 모든 댓글 조회 서비스 테스트
    @Test
    public void getAllPostCommentsTest(){
        Long postId = 1L;
        List<CommentResponseDTO> comments = commentService.getAllPostComments(postId);
        comments.forEach(comment -> {
            log.info(comment.toString());
        });
    }
}
