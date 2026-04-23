package com.cg.filters;
import jakarta.servlet.http.Cookie;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cg.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtService jwtService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                               HttpServletResponse response,
	                               FilterChain filterChain)
	        throws ServletException, IOException {
		

	    String token = null;
	    String username = null;
	    String path = request.getServletPath();

	    if (path.equals("/auth/login") || path.equals("/auth/register")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    // 🔥 1. Try Authorization header
	    String authHeader = request.getHeader("Authorization");

	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        token = authHeader.substring(7);
	    }

	    // 🔥 2. If no header, try cookie
	    if (token == null && request.getCookies() != null) {
	        for (Cookie cookie : request.getCookies()) {
	            if ("refreshToken".equals(cookie.getName())) {
	                token = cookie.getValue();
	                break;
	            }
	        }
	    }

	    // 🔥 3. Extract username only once
	    if (token != null) {
	        try {
	            username = jwtService.extractUsername(token);
	        } catch (Exception e) {
	            System.out.println("Invalid token: " + e.getMessage());
	        }
	    }

	    // 🔥 4. Authenticate
	    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

	        if (jwtService.validateToken(token, userDetails)) {

	            List<String> roles = jwtService.getRoles(token);
	            var authorities = roles.stream()
	                    .map(SimpleGrantedAuthority::new)
	                    .toList();

	            UsernamePasswordAuthenticationToken authToken =
	                    new UsernamePasswordAuthenticationToken(username, null, authorities);

	            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	            SecurityContextHolder.getContext().setAuthentication(authToken);
	        }
	    }

	    filterChain.doFilter(request, response);
	}

}
