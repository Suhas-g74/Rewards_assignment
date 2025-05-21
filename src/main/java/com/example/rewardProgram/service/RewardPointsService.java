package com.example.rewardProgram.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.rewardProgram.entity.Transactions;
import com.example.rewardProgram.model.RewardRequest;
import com.example.rewardProgram.model.RewardResponse;
import com.example.rewardProgram.model.TransactionRequest;
import com.example.rewardProgram.repository.transactionRepository;

import jakarta.transaction.Transactional;

@Service
public class RewardPointsService  {
	@Autowired
	private transactionRepository transRepository;
	
	@Autowired
	private RewardResponse rewardResponse;
	
	/**
	 * This method calculates Total reward points and reward points earned on Monthly Basis,
	 *  for the required number of Months shared in request. 
	 */
	@Transactional
	public RewardResponse  calculateRewardPoints(RewardRequest request) throws Exception {
		LocalDate date1= LocalDate.now();
		Integer months=request.getNoOfMonths();
		Integer totalPointsEarned=0;

		HashMap<String, Integer>  monthMap= new HashMap<>();
		validateRewardRequest(request);
		try {	
		List<Transactions> listOfTransactions=transRepository
				.findByUsername(request.getCustomerName());
		for (int i=1;i<=months;i++) {
			int j=i;	
			Integer totalPointsForMonth=listOfTransactions.stream()
				.filter(x->(x.getTransactionDate().isAfter(date1.minusMonths(j)) 
						&& x.getTransactionDate().isBefore(date1.minusMonths(j-1))))
				.map(x->calculatePoints(x.getAmount()))
				.mapToInt(y->y.intValue()).sum();
		monthMap.put(date1.minusMonths(i).getMonth().toString(),totalPointsForMonth);
		totalPointsEarned=totalPointsEarned+totalPointsForMonth;
		}
		rewardResponse.setCustomerName(request.getCustomerName());
		rewardResponse.setTotalRewardPoints(totalPointsEarned);
		rewardResponse.setMonthlyRewards(monthMap);
		return rewardResponse;
		}catch(Exception e) {
			throw new Exception("Error occurred during reward calculation:"+e.getMessage());
		}
	}
	/**
	 * This method calculates reward point for particular transaction
	 */
	
	public Double calculatePoints(Double amount) {
		
		Double moreThanHundred=amount.doubleValue()-100 ;
		Double moreThanFifty = amount.doubleValue()-50;
		double points=0;
		if(moreThanHundred>0)
		{
			points= (moreThanHundred.doubleValue() * 2)+50;
		}else if(moreThanFifty>0){
			points=moreThanFifty * 1;
		}else {	
			System.out.println("no reward points for "+amount);
		}
		return points;
	}
	/**
	 * This method saves transaction details into database
	 */
	 @Transactional
	public String  addTransactionDetails(TransactionRequest transactionRequest) throws Exception  {
		Transactions transactionDAO = new Transactions();
		validate(transactionRequest);
		try {
		transactionDAO.setUsername(transactionRequest.getUserName());
		transactionDAO.setAmount(transactionRequest.getAmount());
		transactionDAO.setTransactionDate(LocalDate.parse(transactionRequest.getTransactionDate()));
		transRepository.saveAndFlush(transactionDAO);
		
		return "Transaction added succssfully";
		} catch(Exception e) {
			 throw new Exception(e.getMessage());
		}
		
	}
	 /**
	  * Below method saves validates transaction details sent in request
	  */	 

private void validate(TransactionRequest transactionRequest) {
		if(transactionRequest.getAmount()<=0) {
			throw new IllegalArgumentException("Amount should not be 0/Negative value");
		}else if(!StringUtils.hasLength(transactionRequest.getUserName()) ) {
			throw new IllegalArgumentException("user/customer name should not be empty");
		}else if(!StringUtils.hasLength(transactionRequest.getTransactionDate())) {
			throw new IllegalArgumentException("date name should not be empty");
		}
		if(!validateDate(transactionRequest.getTransactionDate())) {
			throw new IllegalArgumentException("date format is incorrect, follow yyyy-mm-dd format");
		}
		
	}
	private Boolean validateDate(String transactionDate) {
		Boolean valid=false;
		try{
			LocalDate.parse(transactionDate);
			 valid=true;
		}catch(Exception e ){
			valid=false;
		}
			
	return valid;
		
	}
	/**
	  * Below method saves validates reward request details.
	  */
	private void validateRewardRequest(RewardRequest request) {
		if(!StringUtils.hasLength(request.getCustomerName())) {
			throw new IllegalArgumentException("user/customer name should not be empty");
		}else if(request.getNoOfMonths()<=0) {
			throw new IllegalArgumentException("Month should not be 0/Negative value");
		}}

	/**
	  * This method fetch transaction details for the requested Customer.
	  */
	@Transactional
	public List<Transactions>  fetchAllTransactions(String customerName) {
	if(!StringUtils.hasLength(customerName) ) {
			throw new IllegalArgumentException("user/customer name should not be empty");
	}
		return transRepository.findByUsername(customerName);
	}
}
