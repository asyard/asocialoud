package org.yardimci.asocialoud.members.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.yardimci.asocialoud.members.controller.MemberResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Autowired
    ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
			throws IOException, ServletException {

		MemberResponse loginFail = new MemberResponse();
		loginFail.setStatus(HttpStatus.FORBIDDEN.toString());
		loginFail.setData("access denied");
		String jwtResponse = objectMapper.writeValueAsString(loginFail);
		response.setContentType("application/json");
		response.getWriter().write(jwtResponse);
		response.setStatus(403);

	}

}