package com.example.rewardProgram.model;

import org.springframework.stereotype.Component;

import lombok.Data;
/**
 * Transactions model used to fetch and save transactions details 
 */
@Data
@Component
public class TransactionRequest {
	private String userName;
	private String transactionDate;
	private Double amount;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
