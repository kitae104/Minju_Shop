package kr.inhatc.spring.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  

        @Override
//        인증되지 않은 사용자가 리소스를 요청할 경우, Unauthorized 에러 발생하고 나머지는 로그인 페이지로 리다이렉트.
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException{
            if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
            }else {
                response.sendRedirect("/member/login");
            }
        }
    }

