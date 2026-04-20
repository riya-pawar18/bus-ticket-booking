package com.cg.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

import com.cg.config.*;

@Component
public class JwtService 
{
	@Autowired
	public JwtConfig jwtConfig;
	 
	public String generateToken(String username, List<String> roles)
	{
		Map<String, Object> claims= new HashMap<>();
		claims.put("roles",roles);
		return createToken(claims,username);
	}
	
	public String createToken(Map<String, Object> claims,String username)
	{
		return Jwts.builder().claims(claims).subject(username).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+jwtConfig.getTokenExpiration()))
				.signWith(jwtConfig.getSecretKey())
				.compact();
	}
	
	public Claims extractAllClaims(String token)
	{
		return Jwts.parser().verifyWith(jwtConfig.getSecretKey()).build()
				.parseSignedClaims(token).getPayload();
	}
	public String extractUsername(String token)
	{
		return extractClaim(token, Claims::getSubject);
	}
	public Date extractExpiration(String token)
	{
		return extractClaim(token, Claims::getExpiration);
	}
	public boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date());
	}
	
	public <T> T extractClaim(String token,Function<Claims, T> claimsResolver)
	{
		final Claims claims= extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public Boolean validateToken(String token, UserDetails userDetails)
	{
		final String username= extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && userDetails.isEnabled());
	}
	public List<String> getRoles(String token) {
		return extractClaim(token, claims -> {
			Object rolesClaim = claims.get("roles");
			if (rolesClaim instanceof List<?>) {
				return ((List<?>) rolesClaim).stream()
						// .map(e->e.toString())
						.map(Object::toString).toList();
			}
			return List.of(); // empty list if claim missing
		});
	}

}
