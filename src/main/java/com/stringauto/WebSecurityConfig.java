package com.stringauto;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stringauto.security.AuthEntryPoint;
import com.stringauto.security.AuthFailureHandler;
import com.stringauto.security.AuthSuccessHandler;
import com.stringauto.security.db.DBAuthenticationProvider;
import com.stringauto.security.db.DBLoginProcessingFilter;
import com.stringauto.security.jwt.JwtAuthenticationProvider;
import com.stringauto.security.jwt.JwtTokenAuthenticationProcessingFilter;
import com.stringauto.security.jwt.SkipPathRequestMatcher;
import com.stringauto.security.jwt.TokenExtractor;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthEntryPoint authenticationEntryPoint;

	@Autowired
	private AuthSuccessHandler successHandler;
	@Autowired
	private AuthFailureHandler failureHandler;

	@Autowired
	private DBAuthenticationProvider dbAuthenticationProvider;

	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;

	@Autowired
	private TokenExtractor tokenExtractor;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ObjectMapper objectMapper;

	public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
	public static final String LOGIN_ENTRY_POINT = "/api/login";
	public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**";
	public static final String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// disable page caching
		// httpSecurity.headers().cacheControl();

		http.csrf().disable() // We don't need CSRF for JWT based authentication
				.exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and().authorizeRequests().antMatchers(LOGIN_ENTRY_POINT).permitAll() // Login end-point
				.antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll() // Token refresh end-point

				.and().authorizeRequests().antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated() // Protected API
																										// End-points
				.and().addFilterBefore(buildDBLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(),
						UsernamePasswordAuthenticationFilter.class);
	}

	protected DBLoginProcessingFilter buildDBLoginProcessingFilter() throws Exception {
		DBLoginProcessingFilter filter = new DBLoginProcessingFilter(LOGIN_ENTRY_POINT, successHandler, failureHandler,
				objectMapper);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}

	protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
		List<String> pathsToSkip = Arrays.asList(TOKEN_REFRESH_ENTRY_POINT, LOGIN_ENTRY_POINT);
		SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
		JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(failureHandler,
				tokenExtractor, matcher);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(dbAuthenticationProvider);
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
				"/swagger-ui.html", "/webjars/**");
	}
}
