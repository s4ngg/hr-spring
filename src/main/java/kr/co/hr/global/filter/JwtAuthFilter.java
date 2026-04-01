package kr.co.hr.global.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.hr.global.config.JwtProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // 로그인 요청은 필터 통과
        if (path.equals("/api/login")
        	    || path.startsWith("/swagger-ui")
        	    || path.startsWith("/v3/api-docs")
        	    || path.startsWith("/api/it-contact")

        	    || path.startsWith("/api/department")
        	    || path.startsWith("/api/find-password")) {
        	    filterChain.doFilter(request, response);
        	    return;
        	}

        // Authorization 헤더에서 토큰 꺼내기
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return;
        }

        // "Bearer " 제거하고 토큰만 추출
        String token = authHeader.substring(7);

        try {
            Long memberId = jwtProvider.getLoginIdFromToken(token);
            request.setAttribute("memberId", memberId);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}