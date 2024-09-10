package com.example.rewardProgram.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.example.rewardProgram.entity.Transactions;
import com.example.rewardProgram.model.RewardRequest;
import com.example.rewardProgram.model.RewardResponse;
import com.example.rewardProgram.model.TransactionData;
import com.example.rewardProgram.model.TransactionRequest;
import com.example.rewardProgram.repository.transactionRepository;

import jakarta.transaction.Transactional;

@Service
public class RewardPointsService  {
	@Autowired
	private transactionRepository transRepository;
	
	@Autowired
	private RewardResponse rewardResponse;
	
	
	// no of transactions
	//calculate rewards for each
	// monthly total and total of all 3 months
	@Transactional
	public RewardResponse  calculateRewardPoints(RewardRequest request) {
		LocalDate date1= LocalDate.now();
		Integer totalPointsEarned=0;
		HashMap<String, Integer>  monthMap= new HashMap<>();
		try {	
		List<Transactions> listOfTransactions=transRepository.findAll();
		for (int i=1;i<=3;i++) {
			int j=i;
		List<Double> pointsEarnedForTransaction=listOfTransactions.stream()
				.filter(x->x.getUsername().equalsIgnoreCase(request.getCustomerName()))
				.filter(x->x.getTransactionDate().isAfter(date1.minusMonths(j)))
				.filter(x->x.getTransactionDate().isBefore(date1.minusMonths(j-1)))
				.map(x->calculatePoints(x.getAmount()))
				.collect(Collectors.toList());
		Integer totalPointsForMonth=pointsEarnedForTransaction.stream()
				.mapToInt(y->y.intValue()).sum();
		monthMap.put(date1.minusMonths(i).getMonth().toString(),totalPointsForMonth);
		totalPointsEarned=totalPointsEarned+totalPointsForMonth;
		}
		rewardResponse.setCustomerName(request.getCustomerName());
		rewardResponse.setTotalRewardPoints(totalPointsEarned);
		rewardResponse.setMonthlyRewards(monthMap);
		return rewardResponse;
		}catch(Exception e) {
			
			return rewardResponse;
		}
	}
	
	
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
	 @Transactional
	public String  addTransactionDetails(TransactionRequest transactionRequest) throws Exception {
		Transactions transactionDAO = new Transactions();
		
		try {
		transactionDAO.setUsername(transactionRequest.getUserName());
		transactionDAO.setAmount(transactionRequest.getAmount());
		transactionDAO.setTransactionDate(LocalDate.parse(transactionRequest.getTransactionDate()));
		//transRepository.save(transactionDAO);
		transRepository.saveAndFlush(transactionDAO);
		
		return "added transaction";
		} catch(Exception e) {
			 throw new Exception(e.getMessage());
		}
		
	}
	 @Transactional
	public List<Transactions>  fetchAllTransactions() {
		return transRepository.findAll();
	}
}
