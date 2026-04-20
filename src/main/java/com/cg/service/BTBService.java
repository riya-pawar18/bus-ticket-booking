package com.cg.service;

import java.util.List;

import com.cg.dtos.BookingRequestDto;
import com.cg.dtos.BusRouteDto;
import com.cg.dtos.PassengerDto;
import com.cg.dtos.RouteScheduleDto;
import com.cg.entity.BusBooking;
import com.cg.entity.RouteSchedule;

public interface BTBService 
{
	public RouteSchedule createSchedule(RouteScheduleDto dto);
	public BusBooking bookTicket(BookingRequestDto dto);
	public List<BusBooking> viewByCustomerBookings(Long custId);
	public List<PassengerDto> viewPassengersByBookingId(Long bookingId);
	public List<RouteScheduleDto> viewSchedules(BusRouteDto br);
	public List<Integer> getBookedSeats(Long scheduleId);
	

}
