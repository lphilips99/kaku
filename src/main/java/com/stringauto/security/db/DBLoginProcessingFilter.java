package com.stringauto.security.db;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stringauto.security.AuthFailureHandler;
import com.stringauto.security.AuthSuccessHandler;

public class DBLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {  
   // private static Logger logger = LoggerFactory.getLogger(DBLoginProcessingFilter.class);

    private final AuthSuccessHandler successHandler;
    private final AuthFailureHandler failureHandler;

    private final ObjectMapper objectMapper;

    public DBLoginProcessingFilter(String defaultProcessUrl, AuthSuccessHandler successHandler, 
            AuthFailureHandler failureHandler, ObjectMapper mapper) {
        super(defaultProcessUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = mapper;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	       if (!HttpMethod.POST.name().equals(request.getMethod())) {
	            if(logger.isDebugEnabled()) {
	                logger.debug("Authentication method not supported. Request method: " + request.getMethod());
	            }
	            //throw new Exception("Authentication method not supported");
	        }

	        LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);

	        if (StringUtils.	isEmpty(loginRequest.getUsername()) || StringUtils.isEmpty(loginRequest.getPassword())) {
	           // throw new Exception("Username or Password not provided");
	        }

	        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

	        return this.getAuthenticationManager().authenticate(token);

	}

   
}
