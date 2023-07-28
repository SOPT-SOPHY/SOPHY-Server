package org.sophy.sophy.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.exception.ErrorStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//AccessDeniedException은 access권한 없는 페이지에 접속할 때 발생하는 SpringSecurity Error(예외처리 인터페이스)
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(mapper.writeValueAsString(
            ApiResponseDto.error(ErrorStatus.FORBIDDEN_USER_EXCEPTION,
                ErrorStatus.FORBIDDEN_USER_EXCEPTION.getMessage())));
    }
}
