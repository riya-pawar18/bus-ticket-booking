package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.config.JwtConfig;
import com.cg.dtos.LoginDto;
import com.cg.dtos.LoginResponseDto;
import com.cg.dtos.RegisterDto;
import com.cg.dtos.SuccessDto;
import com.cg.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthController 
{
	@Autowired
	private AuthService authService;
	@Autowired
	private JwtConfig jwtConfig;
	
	@PostMapping("/register")
	public ResponseEntity<SuccessDto> register(@RequestBody RegisterDto dto)
	{
		return new ResponseEntity<>(authService.register(dto),HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto dto, HttpServletResponse response)
	{
		LoginResponseDto res= authService.login(dto);
		String token= res.getToken();
		String cookie= String.format("refreshToken=%s; HttpOnly; Secure; SameSite=None; Path=/; Max-Age=%d",
				token,jwtConfig.getTokenExpiration());
		response.addHeader("Set-Cookie", cookie);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
}
