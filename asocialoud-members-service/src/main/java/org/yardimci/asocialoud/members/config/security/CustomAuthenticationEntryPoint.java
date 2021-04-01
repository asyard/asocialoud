package org.yardimci.asocialoud.members.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.yardimci.asocialoud.members.controller.MemberResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        MemberResponse loginFail = new MemberResponse();
        String errMsg = "unauthorized request";

        if (e instanceof InsufficientAuthenticationException) {
            errMsg = "bad credentials";
        }
        loginFail.setData(errMsg);
        loginFail.setStatus(HttpStatus.UNAUTHORIZED.toString());
        String jwtResponse = objectMapper.writeValueAsString(loginFail);
        response.setContentType("application/json");
        response.getWriter().write(jwtResponse);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

}
