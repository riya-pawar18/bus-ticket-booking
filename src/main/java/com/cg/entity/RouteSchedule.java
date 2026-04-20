package com.cg.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RouteSchedule 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "route_id")
	private BusRoute busRoute;
	private LocalTime departureTime;
	private LocalDate scheduleDt;
	// Double busFare;
	private Integer avlSeats;
	private Integer totSeats;
	private String schStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BusRoute getBusRoute() {
		return busRoute;
	}
	public void setBusRoute(BusRoute busRoute) {
		this.busRoute = busRoute;
	}
	public LocalTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalDate getScheduleDt() {
		return scheduleDt;
	}
	public void setScheduleDt(LocalDate scheduleDt) {
		this.scheduleDt = scheduleDt;
	}
	public Integer getAvlSeats() {
		return avlSeats;
	}
	public void setAvlSeats(Integer avlSeats) {
		this.avlSeats = avlSeats;
	}
	public Integer getTotSeats() {
		return totSeats;
	}
	public void setTotSeats(Integer totSeats) {
		this.totSeats = totSeats;
	}
	public String getSchStatus() {
		return schStatus;
	}
	public void setSchStatus(String schStatus) {
		this.schStatus = schStatus;
	}
	
	
}
