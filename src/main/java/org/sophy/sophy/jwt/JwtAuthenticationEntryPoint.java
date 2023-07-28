package org.sophy.sophy.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.exception.ErrorStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//이것도 마찬가지로 SpringSecurity에서 발생시키는 Exception 처리 인터페이스
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        //유효한 자격증명을 제공하지 않고 접근하려 할 때 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(mapper.writeValueAsString(
            ApiResponseDto.error(ErrorStatus.INVALID_ACCESS_TOKEN_EXCEPTION,
                ErrorStatus.INVALID_ACCESS_TOKEN_EXCEPTION.getMessage())));
    }
}
