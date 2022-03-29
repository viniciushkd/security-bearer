package com.security.bearer.configuration.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.bearer.dto.UserDTO;

@Component
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private final TokenAuthenticationService tokenAuthenticationService;

    private static final String URI = "/api/v1/login";

    public LoginFilter(TokenAuthenticationService token) {
        super(new AntPathRequestMatcher(URI));
        this.tokenAuthenticationService = token;
        setAuthenticationManager(authentication -> null);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        final UserDTO credentials = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);
        final Authentication auth = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword(), Collections.emptyList());
        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication auth) {
        this.tokenAuthenticationService.addAuthentication(response, auth.getName());
    }
}
