package com.cg.entity;

import java.time.LocalDate;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class BusBooking 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "schedule_id")
	private RouteSchedule schedule;
	
	@ManyToOne
	@JoinColumn(name = "cust_id")
	private Customer customer;
	
	private LocalDate bookingDt;
	
	private String bookingStatus;
	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
	private List<Passenger> passengers;
	public Long getId() {
		return id;
	}
	
	@Column(name = "total_fare", nullable = false)
	private Double totalFare;
	
	public Double getTotalFare() {
		return totalFare;
	}
	public void setTotalFare(Double totalFare) {
		this.totalFare = totalFare;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RouteSchedule getSchedule() {
		return schedule;
	}
	public void setSchedule(RouteSchedule schedule) {
		this.schedule = schedule;
	}	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public LocalDate getBookingDt() {
		return bookingDt;
	}
	public void setBookingDt(LocalDate bookingDt) {
		this.bookingDt = bookingDt;
	}
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
	
	
}

