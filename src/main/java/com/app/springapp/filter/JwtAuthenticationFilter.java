package com.app.springapp.filter;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.enums.SocialProvider;
import com.app.springapp.exception.UserException;
import com.app.springapp.repository.UserDAO;
import com.app.springapp.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDAO userDAO;
    private final ObjectMapper objectMapper;

    // 필터 적용 경로 목록
    private static final List<String> FILTER_PATHS = List.of(
            "/private",
            "/api/test-applications",
            "/api/payments"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        if (path.equals("/api/auth/check")) return false;
        return FILTER_PATHS.stream().noneMatch(path::startsWith);
    }

    private String getAccessTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if ("accessToken".equals(cookie.getName())) return cookie.getValue();
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String accessToken = getAccessTokenFromCookie(request);
        String userEmail = null;
        String socialUserProvider = null;

        if (accessToken == null) {
            sendErrorResponse(response, "토큰 없음");
            return;
        }

        try {
            Claims claims = jwtTokenUtil.parseToken(accessToken);
            userEmail = (String) claims.get("userEmail");
            socialUserProvider = (String) claims.get("socialUserProvider");

            if (userEmail != null && socialUserProvider != null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setUserEmail(userEmail);
                userDTO.setSocialUserProvider(SocialProvider.fromValue(socialUserProvider));
                UserDTO foundUser = userDAO.findUserByUserEmailAndSocialUserProvider(userDTO)
                        .orElseThrow(() -> new UserException("유저 조회 실패", HttpStatus.BAD_REQUEST));

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(foundUser, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, "토큰 만료");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ApiResponseDTO apiResponseDTO = ApiResponseDTO.of(false, message);
        String json = objectMapper.writeValueAsString(apiResponseDTO);
        response.getWriter().write(json);
        response.getWriter().flush();
    }
}


