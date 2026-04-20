package com.cg.dtos;

public class PassengerDto 
{
	private String passengerName;
	private Integer passengerAge;
	private Integer seatNo;
	public Integer getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(Integer seatNo) {
		this.seatNo = seatNo;
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
	
}
