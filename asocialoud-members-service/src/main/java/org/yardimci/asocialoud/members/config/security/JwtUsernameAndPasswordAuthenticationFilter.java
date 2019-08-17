package org.yardimci.asocialoud.members.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.yardimci.asocialoud.members.controller.MemberResponse;
import org.yardimci.asocialoud.members.db.model.Member;
import org.yardimci.asocialoud.members.db.repository.MemberRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authManager;

    private final JwtConfig jwtConfig;

    private ObjectMapper objectMapper;

    private MemberRepository memberRepository;


    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager,
                                                      JwtConfig jwtConfig, ObjectMapper objectMapper, MemberRepository memberRepository) {
        this.authManager = authManager;
        this.jwtConfig = jwtConfig;
        this.objectMapper = objectMapper;
        this.memberRepository = memberRepository;

        // By default, UsernamePasswordAuthenticationFilter listens to "/login" path.
        //this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
        setFilterProcessesUrl(jwtConfig.getUri());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            /* String username = "";
		       String password = "";

                final String authorization = req.getHeader("Authorization");
                if (authorization != null && authorization.startsWith("Basic")) {
                    String base64Credentials = authorization.substring("Basic".length()).trim();
                    String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));

                    final String[] values = credentials.split(":", 2);
                    username = values[0];
                    password = values[1];
		         }

		        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()));
            */

            // 1. Get credentials from request
            UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);

            // 2. Create auth object (contains credentials) which will be used by auth manager
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    creds.getUsername(), creds.getPassword(), Collections.emptyList());

            // 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
            return authManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Upon successful authentication, generate a token.
    // The 'auth' passed to successfulAuthentication() is the current authenticated user.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        User user = (User) auth.getPrincipal();
        long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject(auth.getName())
                // Convert to list of strings.
                // This is important because it affects the way we get them back in the Gateway.
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();

        response.setContentType("application/json");

        // Add token to header
        response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);

        // Return logged in user details
        MemberResponse loginResponse = new MemberResponse();

        Member loggedInMember = memberRepository.findByLoginName(user.getUsername());
        loginResponse.setData(new SuccessfulAuthenticationResponseData(loggedInMember, token));
        loginResponse.setStatus(HttpStatus.OK.toString());

        String jwtResponse = objectMapper.writeValueAsString(loginResponse);
        response.getWriter().write(jwtResponse);
    }

    // A (temporary) class just to represent the user credentials
    private static class UserCredentials {
        private String username, password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private static class SuccessfulAuthenticationResponseData {
        private String /*loginName,*/ realName, token;

        public SuccessfulAuthenticationResponseData(Member member, String token) {
            //this.loginName = member.getLoginName();
            this.realName = member.getRealName();
            this.token = token;
        }

        /*public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }*/

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


}