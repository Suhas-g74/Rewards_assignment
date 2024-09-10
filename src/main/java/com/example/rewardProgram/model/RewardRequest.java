package com.example.rewardProgram.model;

import org.springframework.stereotype.Component;

@Component
public class RewardRequest {
	private String customerName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
}
