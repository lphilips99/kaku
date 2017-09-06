package com.stringauto.service;

import java.util.Optional;

import com.stringauto.model.User;

public interface UserService {
	
	public Optional<User> findByUsername(String username);
	public User findById(Integer id);
	public User findByEmail(String email);
	public void saveUser(User user);

}
