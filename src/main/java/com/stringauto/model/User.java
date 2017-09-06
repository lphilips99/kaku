package com.stringauto.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

	@Entity
	@Table(name = "User")
	public class User implements UserDetails {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "user_id")
		private int id;
		@Column(name = "email")
		@Email(message = "*Please provide a valid Email")
		@NotEmpty(message = "*Please provide an email")
		private String email;
		@Column(name = "password")
		@Length(min = 5, message = "*Your password must have at least 5 characters")
		@NotEmpty(message = "*Please provide your password")
		@Transient
		private String password;
		@Column(name = "name")
		@NotEmpty(message = "*Please provide your name")
		private String name;
		public void setUsername(String username) {
			this.username = username;
		}
		@Column(name = "last_name")
		@NotEmpty(message = "*Please provide your last name")
		private String lastName;
		@Column(name = "active")
		private int active;
		@Column(name = "username")
		private String username;
		
		
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public int getActive() {
			return active;
		}
		public void setActive(int active) {
			this.active = active;
		}
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return false;
		}
		public User orElseThrow(Object object) {
			// TODO Auto-generated method stub
			return null;
		}
		
		/*		@ManyToMany(cascade = CascadeType.ALL)
		@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
		private Set<Role> roles;*/
		
		

}
