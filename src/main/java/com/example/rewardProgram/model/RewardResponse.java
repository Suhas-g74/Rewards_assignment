package com.example.rewardProgram.model;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class RewardResponse {
private Integer totalRewardPoints;
private String customerName;
private HashMap<String, Integer> monthlyRewards;
public Integer getTotalRewardPoints() {
	return totalRewardPoints;
}
public void setTotalRewardPoints(Integer totalRewardPoints) {
	this.totalRewardPoints = totalRewardPoints;
}
public String getCustomerName() {
	return customerName;
}
public void setCustomerName(String customerName) {
	this.customerName = customerName;
}
public HashMap<String, Integer> getMonthlyRewards() {
	return monthlyRewards;
}
public void setMonthlyRewards(HashMap<String, Integer> monthlyRewards) {
	this.monthlyRewards = monthlyRewards;
}

}
