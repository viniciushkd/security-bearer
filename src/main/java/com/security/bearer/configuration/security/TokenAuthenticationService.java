package com.security.bearer.configuration.security;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.security.bearer.configuration.Properties;
import com.security.bearer.repository.UserRepository;
import com.security.bearer.repository.data.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenAuthenticationService {

    private final UserRepository userRepository;
    private final Properties properties;

    static final long EXPIRATION_TIME = 600000;

    @Autowired
    public TokenAuthenticationService(UserRepository userRepository, Properties resources) {
        this.userRepository = userRepository;
        this.properties = resources;
    }

    public void addAuthentication(HttpServletResponse response, String uid) {
        response.addHeader(this.properties.getAuth().getHeader(),
                this.properties.getAuth().getTokenPrefix() + " " + Jwts.builder()
                        .setSubject(uid)
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                        .signWith(SignatureAlgorithm.HS512, this.properties.getAuth().getSecret())
                        .compact());
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(this.properties.getAuth().getHeader());
        if (token != null) {
            try {
                final String uid = Jwts.parser().setSigningKey(this.properties.getAuth().getSecret())
                        .parseClaimsJws(token.replace(this.properties.getAuth().getTokenPrefix(), "")).getBody().getSubject();
                if (uid != null) {
                    if (uid != null) {
                        final Optional<User> user = this.userRepository.findByUid(uid);
                        return user.map(value -> new UsernamePasswordAuthenticationToken(uid, null, value.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList()))).orElse(null);
                    }
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
