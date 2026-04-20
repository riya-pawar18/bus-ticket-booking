package com.cg.service;

import com.cg.repo.RoleRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.cg.dtos.LoginDto;
import com.cg.dtos.LoginResponseDto;
import com.cg.dtos.RegisterDto;
import com.cg.dtos.SuccessDto;
import com.cg.entity.Customer;
import com.cg.entity.Role;
import com.cg.entity.User;
import com.cg.exceptions.BadRequestException;
import com.cg.exceptions.CustomerAreadyExistsException;
import com.cg.exceptions.NotFoundException;
import com.cg.repo.CustomerRepository;
import com.cg.repo.UserRepository;


@Service
public class AuthService 
{
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;
	
		
	public SuccessDto register(RegisterDto dto)
	{
		if(userRepository.existsByUsername(dto.getUsername()))
			throw new CustomerAreadyExistsException("Customer already exists with this username");
		
		User user= new User();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		Customer customer= new Customer();
		customer.setCustName(dto.getName());
		customer.setPhoneNo(dto.getPhoneNo());
		customer.setUser(user);
		customerRepository.save(customer);
		
		Role role = new Role(dto.getUsername(), "ROLE_USER");
		roleRepository.save(role);
		SuccessDto successDto= new SuccessDto("Registered successfully!");
		return successDto;
		
	}
	
	public LoginResponseDto login(LoginDto dto)
	{
	    try {
	        authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                dto.getUsername(),
	                dto.getPassword()
	            )
	        );
	    } 
	    catch (BadCredentialsException e) {
	        throw new BadRequestException("Incorrect password");
	    } 
	    catch (UsernameNotFoundException e) {
	        throw new NotFoundException("User not found");
	    }

	    String token = jwtService.generateToken(dto.getUsername(), List.of("ROLE_USER"));
	    return new LoginResponseDto(token);
	}
	
	public User getCurrentUser()
    {
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated())
            throw new BadRequestException("Unauthorized");;
        String username=  (String) authentication.getPrincipal();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("Customer not found"));

    }

}
