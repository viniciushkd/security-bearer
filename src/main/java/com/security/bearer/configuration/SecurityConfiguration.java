package com.security.bearer.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.bearer.configuration.security.AuthenticationFilter;
import com.security.bearer.configuration.security.CustomAuthenticationProvider;
import com.security.bearer.configuration.security.LoginFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final CustomAuthenticationProvider authenticationProvider;
	private final AuthenticationFilter authenticationFilter;
	private final LoginFilter loginFilter;

	@Autowired
	public SecurityConfiguration(CustomAuthenticationProvider authenticationProvider,
			AuthenticationFilter authenticationFilter, LoginFilter loginFilter) {
		this.authenticationProvider = authenticationProvider;
		this.authenticationFilter = authenticationFilter;
		this.loginFilter = loginFilter;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(this.authenticationProvider);
	}

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		this.loginFilter.setAuthenticationManager(authenticationManager());
		httpSecurity.csrf().disable()
				.addFilterBefore(this.loginFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		httpSecurity.cors();

	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/h2", "/h2/**");
	}

}
