package com.cg.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class RouteScheduleDto 
{
	 private Long scheduleId; 
	private Long routeId;
	private LocalTime departureTime;
	private LocalDate scheduleDt;
	private Integer avlSeats;
	private Integer totSeats;
	private Double busFare;
	
	   public Long getScheduleId() { return scheduleId; }
	    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }
	public Double getBusFare() {
		return busFare;
	}
	public void setBusFare(Double busFare) {
		this.busFare = busFare;
	}
	public Long getRouteId() {
		return routeId;
	}
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
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
	
}
