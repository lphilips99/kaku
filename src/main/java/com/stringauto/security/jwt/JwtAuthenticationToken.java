package com.stringauto.security.jwt;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.stringauto.security.model.AuthenticatedUser;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	 private static final long serialVersionUID = 2877954820905567501L;

	    private RawAccessJwtToken rawAccessToken;
	    private AuthenticatedUser authenticatedUser;

	    public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
	        super(null);
	        this.rawAccessToken = unsafeToken;
	        this.setAuthenticated(false);
	    }

	    public JwtAuthenticationToken(AuthenticatedUser authenticatedUser, Collection<? extends GrantedAuthority> authorities) {
	        super(authorities);
	        this.eraseCredentials();
	        this.authenticatedUser = authenticatedUser;
	        super.setAuthenticated(true);
	    }

	    @Override
	    public void setAuthenticated(boolean authenticated) {
	        if (authenticated) {
	            throw new IllegalArgumentException(
	                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
	        }
	        super.setAuthenticated(true);
	    }

	    @Override
	    public Object getCredentials() {
	        return rawAccessToken;
	    }

	    @Override
	    public Object getPrincipal() {
	        return this.authenticatedUser;
	    }

	    @Override
	    public void eraseCredentials() {        
	        super.eraseCredentials();
	        this.rawAccessToken = null;
	    }

}
