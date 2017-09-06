package com.stringauto.security.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.stringauto.model.User;
import com.stringauto.security.model.AuthenticatedUser;
import com.stringauto.service.UserService;

@Component
public class DBAuthenticationProvider implements AuthenticationProvider{
	
	 private final UserService userService;

	@Autowired
    public DBAuthenticationProvider(final UserService userService) {
        this.userService = userService;
       // this.encoder = encoder;
    }	
	
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = userService.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("User not found: " + userName));

        /*
        	if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }

        if (user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());
		*/
        AuthenticatedUser authenticatedUser =  new AuthenticatedUser(userName);

        return new UsernamePasswordAuthenticationToken(authenticatedUser, null, null);

	}

	@Override
	public boolean supports(Class<?> authentication) {
	       return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
