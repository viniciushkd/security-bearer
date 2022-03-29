package com.security.bearer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.bearer.repository.data.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.user = :usr and u.password = :psswd")
	Optional<User> findByUserAndPassword(@Param("usr") String usr, @Param("psswd") String psswd);
	
	User findByUser(String user);

	Optional<User> findByUid(String uid);
	
}
