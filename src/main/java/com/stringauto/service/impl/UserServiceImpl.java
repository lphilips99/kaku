package com.stringauto.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stringauto.model.User;
import com.stringauto.repository.UserRepository;
import com.stringauto.service.UserService;





@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
	}

	@Override
	public User findByEmail(String email) {
		return null;
	}


	@Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }


	@Override
	public User findById(Integer userId) {
		 return this.userRepository.findById(userId);
	}

}
