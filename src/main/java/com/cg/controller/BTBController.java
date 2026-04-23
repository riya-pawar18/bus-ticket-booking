package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dtos.BookingRequestDto;
import com.cg.dtos.BusRouteDto;
import com.cg.dtos.PassengerDto;
import com.cg.dtos.RouteScheduleDto;
import com.cg.entity.BusBooking;
import com.cg.entity.RouteSchedule;
import com.cg.service.BTBService;

@RestController
@RequestMapping("/bus")
public class BTBController 
{
	@Autowired
	private BTBService service;
	
	@PostMapping("/schedule")
	public ResponseEntity<RouteSchedule> createSchedule(@RequestBody RouteScheduleDto dto)
	{
		return new ResponseEntity<>(service.createSchedule(dto),HttpStatus.CREATED);
	}
	@PostMapping("/book-ticket")
	public ResponseEntity<BusBooking> bookTicket(@RequestBody BookingRequestDto dto)
	{
		
		return new ResponseEntity<>(service.bookTicket(dto),HttpStatus.CREATED);
	}
	
	@GetMapping("/bookings/customer")
	public ResponseEntity<List<BusBooking>> viewByCustomerBookings()
	{
		return new ResponseEntity<>(service.viewByCustomerBookings(),HttpStatus.OK);
	}
	
	@GetMapping("/bookings/passengers/{bookingId}")
	public ResponseEntity<List<PassengerDto>> viewPassengersByBookingId(@PathVariable Long bookingId)
	{
		return new ResponseEntity<>(service.viewPassengersByBookingId(bookingId),HttpStatus.OK);
	}
	
	@GetMapping("/schedules")
	public ResponseEntity<List<RouteScheduleDto>> viewSchedules(
	        @RequestParam String source,
	        @RequestParam String dest) 
	{
	    BusRouteDto dto = new BusRouteDto();
	    dto.setSource(source);
	    dto.setDest(dest);

	    return new ResponseEntity<>(service.viewSchedules(dto), HttpStatus.OK);
	}
	
	@GetMapping("/booked/{scheduleId}")
	public ResponseEntity<List<Integer>> getBookedSeats(@PathVariable Long scheduleId)
	{
		return new ResponseEntity<>(service.getBookedSeats(scheduleId),HttpStatus.OK);
	}
	
}
