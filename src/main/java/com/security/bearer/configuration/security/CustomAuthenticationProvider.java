package com.security.bearer.configuration.security;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.security.bearer.component.Encrypt;
import com.security.bearer.repository.UserRepository;
import com.security.bearer.repository.data.User;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private final UserRepository userRepository;
	private final Encrypt encrypt;
	
	@Autowired
	public CustomAuthenticationProvider(UserRepository userRepository, Encrypt encrypt) {
		super();
		this.userRepository = userRepository;
		this.encrypt = encrypt;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final Optional<User> user = this.userRepository.findByUserAndPassword(authentication.getName(), this.encrypt.md5(authentication.getCredentials().toString()));
		return user.isPresent() ?
				new UsernamePasswordAuthenticationToken(user.get().getUid(), authentication.getCredentials(), new ArrayList<>()) :
				null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
