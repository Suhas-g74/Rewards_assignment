package com.example.rewardProgram.model;

import org.springframework.stereotype.Component;
/**
 * request model class used to fetch total rewards  
 */
@Component
public class RewardRequest {
	private String customerName;
	private Integer noOfMonths;

	public Integer getNoOfMonths() {
		return noOfMonths;
	}

	public void setNoOfMonths(Integer noOfMonths) {
		this.noOfMonths = noOfMonths;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
}
