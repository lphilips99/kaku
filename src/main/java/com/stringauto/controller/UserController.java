package com.stringauto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stringauto.model.User;
import com.stringauto.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

@Api(basePath = "/api/user", value = "users", description = "Operations with users", produces = "application/json")

@RequestMapping("/api")
@RestController
public class UserController {

	@Autowired
	private UserService userService ;
	
	@ApiOperation(value = "Get User", notes = "Get User")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUser() {

		/*List<User> users = userService.
		if (users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);*/
		

		return null;
	}
	
	@ApiOperation(value = "Get User", notes = "Get User")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("id") int id) throws Exception {

		
		User user = userService.findById(id);
		
		if (user == null) {
			throw new Exception("User not found");
			
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
		
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(User u) {
		return new ResponseEntity<User>(new User(), HttpStatus.OK);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public ResponseEntity<List<User>> modifyUser(@PathVariable("id") int id) {
		return new ResponseEntity<List<User>>(new ArrayList<>(), HttpStatus.OK);
		
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
		return new ResponseEntity<User>(new User(), HttpStatus.OK);

	}
}
