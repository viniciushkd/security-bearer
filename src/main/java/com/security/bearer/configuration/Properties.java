package com.security.bearer.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class Properties {

	private Auth auth = new Auth();
	
	public Auth getAuth() {
		return auth;
	}
	public void setAuth(Auth auth) {
		this.auth = auth;
	}
	
	public class Auth {
		private String secret;
		private String tokenPrefix;
		private String header;
		
		public String getSecret() {
			return secret;
		}
		public String getTokenPrefix() {
			return tokenPrefix;
		}
		public String getHeader() {
			return header;
		}
		public void setSecret(String secret) {
			this.secret = secret;
		}
		public void setTokenPrefix(String tokenPrefix) {
			this.tokenPrefix = tokenPrefix;
		}
		public void setHeader(String header) {
			this.header = header;
		}
	}
}
