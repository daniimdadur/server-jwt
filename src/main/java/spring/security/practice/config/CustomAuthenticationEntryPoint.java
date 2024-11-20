package spring.security.practice.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import spring.security.practice.base.ResponseError;
import spring.security.practice.constant.MessageApp;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(403);
        response.getWriter().write(ResponseError.builder()
                        .statusCode(HttpStatus.FORBIDDEN.value())
                        .message(MessageApp.FORBIDDEN)
                        .errors(MessageApp.FORBIDDEN)
                .build()
                .toString());
    }
}
