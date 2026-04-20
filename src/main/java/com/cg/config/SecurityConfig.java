package com.cg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cg.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig 
{
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider provider) throws Exception
	{
		return http.csrf(csrf->csrf.disable())
				.authenticationProvider(provider)
				.authorizeHttpRequests(auth->auth.requestMatchers("/auth/login", "/auth/register").permitAll())
				.authorizeHttpRequests(auth->auth.requestMatchers("/generateToken").permitAll())
				.authorizeHttpRequests(auth->auth.requestMatchers("/bus/**").authenticated())
				.sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
				.build();

	}
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider(
	        UserDetailsService userDetailsService,
	        PasswordEncoder passwordEncoder) {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

}
