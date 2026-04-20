package com.cg.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dtos.BookingRequestDto;
import com.cg.dtos.BusRouteDto;
import com.cg.dtos.CustomerDto;
import com.cg.dtos.PassengerDto;
import com.cg.dtos.RouteScheduleDto;
import com.cg.entity.BusBooking;
import com.cg.entity.BusRoute;
import com.cg.entity.Customer;
import com.cg.entity.Passenger;
import com.cg.entity.RouteSchedule;
import com.cg.entity.User;
import com.cg.exceptions.BadRequestException;
import com.cg.exceptions.InvalidException;
import com.cg.exceptions.NotFoundException;
import com.cg.repo.BusBookingRepository;
import com.cg.repo.BusRouteRepository;
import com.cg.repo.CustomerRepository;
import com.cg.repo.PassengerRepository;
import com.cg.repo.RouteScheduleRepository;
@Service
public class BTBServiceImpl implements BTBService
{
	@Autowired
	private RouteScheduleRepository rsRepo;
	@Autowired
	private BusBookingRepository bsRepo;
	@Autowired
	private BusRouteRepository brRepo;
	@Autowired
	private CustomerRepository cRepo;
	@Autowired
	private PassengerRepository pRepo;
	@Autowired
	private AuthService authService;
	@Override
	public RouteSchedule createSchedule(RouteScheduleDto dto) 
	{
		RouteSchedule schedule= new RouteSchedule();
		Optional<BusRoute> route= brRepo.findById(dto.getRouteId());
		if(route.isEmpty())
			throw new NotFoundException("Bus Route not found!");
		schedule.setBusRoute(route.get()); 
		schedule.setDepartureTime(dto.getDepartureTime());
		schedule.setScheduleDt(dto.getScheduleDt());
		schedule.setAvlSeats(dto.getAvlSeats());
		schedule.setTotSeats(dto.getTotSeats());
		if(dto.getScheduleDt().isBefore(LocalDate.now()))
			schedule.setSchStatus("COMPLETED");
		else if(dto.getScheduleDt().isAfter(LocalDate.now()))
			schedule.setSchStatus("SCHEDULED");
		else 
		{
			if(LocalTime.now().isAfter(dto.getDepartureTime()))
				schedule.setSchStatus("RUNNING");
			else
				schedule.setSchStatus("SCHEDULED");
		}
		return rsRepo.save(schedule);
	}

	
	@Override
	public BusBooking bookTicket(BookingRequestDto dto) 
	{
	    RouteSchedule schedule = rsRepo.findById(dto.getScheduleId())
	            .orElseThrow(() -> new NotFoundException("Route schedule not found"));

	    User user = authService.getCurrentUser();
	    if (user == null) {
	        throw new InvalidException("User not authenticated");
	    }

	    Customer customer = getCustomer();
	    List<PassengerDto> passengers = dto.getPassengers();
	    if (passengers == null || passengers.isEmpty()) {
	        throw new InvalidException("Enter passenger details");
	    }
	    if (!schedule.getSchStatus().equalsIgnoreCase("SCHEDULED")) {
	        throw new InvalidException("Cannot book for this schedule as it has started or completed");
	    }
	    if (schedule.getAvlSeats() < passengers.size()) {
	        throw new InvalidException(passengers.size() + " seats are not available");
	    }
	    BusBooking booking = new BusBooking();
	    booking.setSchedule(schedule);
	    booking.setCustomer(customer);
	    booking.setBookingDt(LocalDate.now());
	    booking.setBookingStatus("CONFIRMED");

	    List<Passenger> passengerEntities = new ArrayList<>();

	    for (PassengerDto p : passengers) {
	        Passenger entity = new Passenger();
	        entity.setPassengerName(p.getPassengerName());
	        entity.setPassengerAge(p.getPassengerAge());
	        entity.setSeatNo(p.getSeatNo());
	        entity.setBooking(booking);

	        passengerEntities.add(entity);
	    }
	    schedule.setAvlSeats(schedule.getAvlSeats() - passengers.size());
	    rsRepo.save(schedule);

	    booking.setPassengers(passengerEntities);

	    return bsRepo.save(booking);
	}
	
	
	@Override
	public List<BusBooking> viewByCustomerBookings() {
		
		Customer customer= getCustomer();
		return bsRepo.findByCustomer(customer);
	}

	@Override
	public List<PassengerDto> viewPassengersByBookingId(Long bookingId) {
		
		BusBooking booking= bsRepo.findById(bookingId).orElseThrow(()->new NotFoundException("Booking does not exist"));
		List<PassengerDto> list= new ArrayList<>();
		for(Passenger p:booking.getPassengers())
		{
			PassengerDto dto= new PassengerDto();
			dto.setPassengerName(p.getPassengerName());
			dto.setPassengerAge(p.getPassengerAge());
			dto.setSeatNo(p.getSeatNo());
			list.add(dto);
		}
		return list;
	}

	@Override
	public List<RouteScheduleDto> viewSchedules(BusRouteDto br) {
		BusRoute route= brRepo.findBySrcIgnoreCaseAndDestIgnoreCase(br.getSource(),br.getDest()).orElseThrow(()-> new NotFoundException("No such bus route exists"));
		List<RouteScheduleDto> list= new ArrayList<>();
		for(RouteSchedule r: route.getSchedules())
		{
			RouteScheduleDto dto= new RouteScheduleDto();
			dto.setDepartureTime(r.getDepartureTime());
			dto.setAvlSeats(r.getAvlSeats());
			dto.setTotSeats(r.getTotSeats());
			dto.setScheduleDt(r.getScheduleDt());
			list.add(dto);
		}
		return list;
	}

	@Override
	public List<Integer> getBookedSeats(Long scheduleId) 
	{
		RouteSchedule routeSchedule= rsRepo.findById(scheduleId).orElseThrow(()->new NotFoundException("Route schedule not found!"));
		
		if(rsRepo.getBookedSeats(scheduleId).size()==routeSchedule.getTotSeats())
			throw new BadRequestException("All seats are booked!");
			
		return rsRepo.getBookedSeats(scheduleId);
	}

	@Override
	public Customer getCustomer() 
	{
	    User user = authService.getCurrentUser();

	    return cRepo.findByUserUsername(user.getUsername())
	            .orElseThrow(() -> new NotFoundException(
	                    "Customer not found for username: " + user.getUsername()));
	}

}
