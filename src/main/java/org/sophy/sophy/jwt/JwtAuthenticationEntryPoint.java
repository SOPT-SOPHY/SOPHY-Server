package org.sophy.sophy.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//이것도 마찬가지로 SpringSecurity에서 발생시키는 Exception 처리 인터페이스
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //유효한 자격증명을 제공하지 않고 접근하려 할 때 401
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
