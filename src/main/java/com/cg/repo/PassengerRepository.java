package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Passenger;
import com.cg.entity.BusBooking;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long>{

	
}
