package com.cg.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class BusRoute 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String src;
	private String dest;
	@JsonIgnore
	@OneToMany(mappedBy = "busRoute")
	private List<RouteSchedule> schedules;
	
	public List<RouteSchedule> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<RouteSchedule> schedules) {
		this.schedules = schedules;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	

}
