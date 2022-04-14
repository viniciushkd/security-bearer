package com.security.bearer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.bearer.business.UserBusiness;
import com.security.bearer.dto.ResponseDTO;
import com.security.bearer.dto.UserDTO;
import com.security.bearer.dto.UserRDTO;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserBusiness business;

	@PostMapping("/user")
	public ResponseEntity<ResponseDTO<?>> newUser(@RequestBody ResponseDTO<UserDTO> dto) {
		business.saveUser(dto.getDto().getUsername(), dto.getDto().getPassword());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/user/{uidUser}")
	public ResponseEntity<ResponseDTO<UserRDTO>> getUser(@PathVariable String uidUser) {
		UserRDTO user;
		user = business.getUser(uidUser);
		return new ResponseEntity<>(new ResponseDTO<UserRDTO>(user), HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<ResponseDTO<List<UserRDTO>>> listUsers() {
		List<UserRDTO> users = business.listUser();
		return new ResponseEntity<>(new ResponseDTO<List<UserRDTO>>(users), HttpStatus.OK);
	}
}
