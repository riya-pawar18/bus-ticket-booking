package com.cg.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User ,String> 
{
	public Optional<User> findByUsername(String username);
	public boolean existsByUsername(String username);
}
