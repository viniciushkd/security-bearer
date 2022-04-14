package com.security.bearer.business;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.security.bearer.component.Encrypt;
import com.security.bearer.dto.RoleDTO;
import com.security.bearer.dto.UserRDTO;
import com.security.bearer.repository.RoleRepository;
import com.security.bearer.repository.UserRepository;
import com.security.bearer.repository.data.ERole;
import com.security.bearer.repository.data.Role;
import com.security.bearer.repository.data.User;

@Component
public class UserBusiness {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private Encrypt encrypt;

	public UserBusiness(UserRepository userRepository, RoleRepository roleRepository, Encrypt encrypt) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encrypt = encrypt;
	}

	public void saveUser(String user, String password) {
		Optional<Role> role = this.roleRepository.findByName(ERole.ROLE_USER);
		if (role.isPresent()) {
			this.userRepository.save(new User(user, this.encrypt.md5(password), UUID.randomUUID().toString(),
					Arrays.asList(role.get())));
		}
	}

	public UserRDTO getUser(String uidUser) {
		final Optional<User> optional = userRepository.findByUid(uidUser);
		if (optional.isPresent()) {
			return optional.map(user -> new UserRDTO(user.getUser(), user.getUid(),
					user.getRoles().stream().map(roles -> new RoleDTO(roles.getName())).collect(Collectors.toList()))).get();
		} else {
			return new UserRDTO("", "", null);
		}
	}

	public List<UserRDTO> listUser() {
		final List<User> user = userRepository.findAll();
		return user.stream()
				.map(usr -> new UserRDTO(usr.getUser(), usr.getUid(), usr.getRoles().stream()
						.map(roles -> new RoleDTO(roles.getName())).collect(Collectors.toList())))
				.collect(Collectors.toList());
	}
}
