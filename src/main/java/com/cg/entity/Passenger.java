package com.cg.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Passenger 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String passengerName;
	private Integer passengerAge;
	private Integer seatNo;
	@ManyToOne
	@JoinColumn(name = "booking_id")
	@JsonIgnore
	private BusBooking booking;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public Integer getPassengerAge() {
		return passengerAge;
	}
	public void setPassengerAge(Integer passengerAge) {
		this.passengerAge = passengerAge;
	}
	public Integer getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(Integer seatNo) {
		this.seatNo = seatNo;
	}
	public BusBooking getBooking() {
		return booking;
	}
	public void setBooking(BusBooking booking) {
		this.booking = booking;
	}
	

}
