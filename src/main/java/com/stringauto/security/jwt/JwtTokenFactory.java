package com.stringauto.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.stringauto.security.model.AuthenticatedUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenFactory {

    private final JwtSettings settings;

    @Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }
    
	public JwtToken createAccessJwtToken(AuthenticatedUser userContext) {
		 if (StringUtils.isEmpty(userContext.getUsername())) 
	            throw new IllegalArgumentException("Cannot create JWT Token without username");

	 /*       if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) 
	            throw new IllegalArgumentException("User doesn't have any privileges");
	       */    

	        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
	       // claims.put("scopes", userContext.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));

	        LocalDateTime currentTime = LocalDateTime.now();
	        
	        String token = Jwts.builder()
	          .setClaims(claims)
	          .setIssuer(settings.getTokenIssuer())
	          .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
	          .setExpiration(Date.from(currentTime
	              .plusMinutes(settings.getTokenExpirationTime())
	              .atZone(ZoneId.systemDefault()).toInstant()))
	          .signWith(SignatureAlgorithm.HS512, settings.getSecret())
	        .compact();

	        return new JwtToken(token);
        
	
        
        
	}

	public JwtToken createRefreshToken(AuthenticatedUser userContext) {
		// TODO Auto-generated method stub
		return null;
	}

}
