package com.cg.dtos;

public class CustomerDto 
{
	private String custName;
	private String phoneNo;
	private String username;
	private Long custId;
	public String getCustName() {
		return custName;
	}
	public void setCustName(String name) {
		this.custName = name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}

}
