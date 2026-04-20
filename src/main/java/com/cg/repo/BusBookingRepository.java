package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.BusBooking;
import com.cg.entity.Customer;
@Repository
public interface BusBookingRepository extends JpaRepository<BusBooking, Long>
{
	List<BusBooking> findByCustomer(Customer customer);

}
