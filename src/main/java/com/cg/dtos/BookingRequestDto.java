package com.cg.dtos;

import java.util.List;

public class BookingRequestDto 
{
	private Long scheduleId;
	private Long custId;
	private List<PassengerDto> passengers;
	public List<PassengerDto> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<PassengerDto> passengers) {
		this.passengers = passengers;
	}
	public Long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	
	
	
}
