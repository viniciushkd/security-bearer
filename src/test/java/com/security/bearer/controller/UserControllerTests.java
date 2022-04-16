package com.security.bearer.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.security.bearer.component.Encrypt;
import com.security.bearer.dto.UserDTO;
import com.security.bearer.repository.RoleRepository;
import com.security.bearer.repository.UserRepository;
import com.security.bearer.repository.data.ERole;
import com.security.bearer.repository.data.Role;
import com.security.bearer.repository.data.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private Encrypt encrypt;

	final static String UID = UUID.randomUUID().toString();
	String headerValue = "";
	UserDTO dto;

	@Before
	public void insertData() throws Exception {
		roleRepository.save(new Role(ERole.ROLE_USER));
		roleRepository.save(new Role(ERole.ROLE_ADMIN));
		userRepository.save(new User("admin", this.encrypt.md5("admin"), UID,
				Arrays.asList(roleRepository.findByName(ERole.ROLE_ADMIN).get())));
		MvcResult mvcResult = mockMvc.perform(post("/api/v1/login").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n"
						+ "    \"username\": \"admin\",\r\n"
						+ "    \"password\": \"admin\"\r\n"
						+ "}")).andReturn();
		headerValue = mvcResult.getResponse().getHeader("Authorization");
		dto = new UserDTO("admin", "admin");

	}
	
	@After
	public void removeData() {
		userRepository.deleteAll();
		roleRepository.deleteAll();
	}
	
	@Test
	public void saveUser() throws Exception {
		this.mockMvc.perform(
				post("/api/v1/user").header("Authorization", headerValue).contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n"
						+ "    \"dto\": {\r\n"
						+ "        \"username\": \"user1\",\r\n"
						+ "        \"password\": \"user1\"\r\n"
						+ "    }\r\n"
						+ "}"))
				.andExpect(status().isOk());
	}

	@Test
	public void getUser() throws Exception {
		this.mockMvc.perform(
				get("/api/v1/user/" + UID).header("Authorization", headerValue).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void notGetUser() throws Exception {
		this.mockMvc.perform(
				get("/api/v1/user/" + "xxxxx").header("Authorization", headerValue).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void listUsers() throws Exception {
		this.mockMvc.perform(
				get("/api/v1/user").header("Authorization", headerValue).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testUserDTO() {
		assertThat(dto.getUsername()).isEqualTo("admin");
		assertThat(dto.getPassword()).isEqualTo("admin");
	}

}
