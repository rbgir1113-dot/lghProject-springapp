package com.app.springapp.service;

import com.app.springapp.domain.dto.PostDTO;
import com.app.springapp.domain.dto.request.PostRequestDTO;
import com.app.springapp.domain.dto.response.PostResponseDTO;
import com.app.springapp.domain.dto.response.PostSelectResponseDTO;
import com.app.springapp.domain.vo.PostVO;
import com.app.springapp.exception.PostException;
import com.app.springapp.repository.PostDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, PostException.class})
public class PostServiceImpl implements PostService {
    private final PostDAO postDAO;
    private final CommunityAuthService communityAuthService;

    //    로그인 되기 전 까지 유저 아이디 관련하는거 담당하는 매서드 임시 정의
    public Long getUserId(){
        return 3L;
    }

    @Override
    public Map<String, Object> getAllPosts(Map<String, Object> req) {
        int page = (Integer) req.get("page");
        int size = 4;
        int offset = (page - 1) * size;
        int totalPages = 0;
        String postTag = (String) req.get("postTag");
        log.info("post tag 테스트 용: {}", postTag);

        Map<String, Object> filters = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        filters.put("size", size);
        filters.put("offset", offset);
        filters.put("postTag", postTag);

        // 포스트 사이즈는 얼마인지, 무엇인지 각각 반환 하는거도 생각 해야함
        // 조건에 해당 하는 포스트 불러오기
        List<PostResponseDTO> posts = postDAO.findAll(filters).stream()
                .map(PostResponseDTO::from)
                .collect(Collectors.toList());

        // 조건에 해당하는 포스트의 총 갯수 보기
        int postCounts = postDAO.findCount(postTag);
        if(postCounts % size == 0){
            totalPages = postCounts / size;
        } else {
            totalPages = (postCounts / size) + 1;
        }

        result.put("posts", posts);
        result.put("currentPage", page);
        result.put("totalPages", totalPages);
        result.put("size", size);
        result.put("postCounts", postCounts);

        return result;
    }

//    특정 게시글 조회
    @Override
    public PostSelectResponseDTO getPost(Long id) {
//        조회수 증가
        this.increasePostReadCount(id);

//        현재 유저 정보 가져오기
        Long userId = communityAuthService.getUserId();

        PostDTO postDTO = new PostDTO();
        postDTO.setId(id);
        postDTO.setUserId(userId);

//        게시글 불러오기 (없다면 예외)
        PostDTO post = postDAO.findById(postDTO).orElseThrow(() -> {
            throw new PostException(HttpStatus.BAD_REQUEST, "포스트 불러오기 실패");
        });

//        게시글 반환
        return PostSelectResponseDTO.from(post);
    }

//    특정 유저의 프로필 에서 해당 유저가 작성 한 모든 게시글 보여주기
    @Override
    public Map<String, Object> getUserPosts(Long userId, Map<String, Object> req) {
        int page = (Integer) req.get("page");
        int size = 4;
        int offset = (page - 1) * size;
        int totalPages = 0;

        Map<String, Object> filters = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        filters.put("size", size);
        filters.put("offset", offset);
        filters.put("userId", userId);

        List<PostResponseDTO> posts = postDAO.findByUserId(filters).stream()
                .map(PostResponseDTO::from)
                .collect(Collectors.toList());

        // 조건에 해당하는 포스트의 총 갯수 보기
        int postCounts = postDAO.countByUserId(userId);
        if(postCounts % size == 0){
            totalPages = postCounts / size;
        } else {
            totalPages = (postCounts / size) + 1;
        }

        result.put("posts", posts);
        result.put("currentPage", page);
        result.put("totalPages", totalPages);
        result.put("size", size);
        result.put("postCounts", postCounts);

        return result;
    }

//    게시글 작성
    @Override
    public void writePost(PostRequestDTO postRequestDTO) {
        PostVO postVO = PostVO.from(postRequestDTO);
        postVO.setUserId(getUserId());
        try {
            postDAO.save(postVO);
        } catch (Exception e) {
            throw new PostException(HttpStatus.BAD_REQUEST, "게시글 작성 실패");
        }
    }

//    게시글 수정
    @Override
    public void updatePost(Long id, PostRequestDTO postRequestDTO) {
        Long userId = getUserId();
        PostVO postVO = PostVO.from(postRequestDTO);
        postVO.setId(id);
        postVO.setUserId(userId);

        if(canTouchPost(id, userId)){
            postDAO.update(postVO);
        } else {
            throw new PostException(HttpStatus.BAD_REQUEST, "해당 게시글 수정 권한 없습니다.");
        }
    }

//    게시글 삭제
    @Override
    public void deletePost(Long id) {
        Long userId = getUserId();
        if(canTouchPost(id, userId)){
            postDAO.updatePostIsDeleted(id);
        } else {
            throw new PostException(HttpStatus.BAD_REQUEST, "해당 게시글 삭제 권한 없습니다.");
        }
    }

//    유저가 해당 게시글 접근 권한 있는지 확인
    @Override
    public boolean canTouchPost(Long id, Long userId) {
        PostVO postVO = new PostVO();
        postVO.setId(id);
        postVO.setUserId(userId);

        return postDAO.existByIdAndUserId(postVO) != 0;
    }

//    게시글 조회수 1 증가
    @Override
    public void increasePostReadCount(Long id) {
        postDAO.updatePostReadCount(id);
    }
}
