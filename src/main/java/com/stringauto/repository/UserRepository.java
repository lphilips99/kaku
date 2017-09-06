package com.stringauto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.stringauto.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String userName);
	User findById(Integer id);
}
