package com.example.rewardProgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.rewardProgram.model.RewardRequest;
import com.example.rewardProgram.model.TransactionRequest;
import com.example.rewardProgram.service.RewardPointsService;

/**
 * This is the controller class which contains API to save  and get transaction details. Also contains API to calculate
 * reward points 
 */
@RestController
@RequestMapping("/api/")
public class RewardPointsController {
	@Autowired
	private RewardPointsService rewardPointsService;
	/**
	 * This returns reward points gained by particular customer 
	 * @param customername should be passed to obtain points
	 */
	@RequestMapping(value="v1/getRewardPoints",method = RequestMethod.GET)
	public ResponseEntity<?>  calculateRewardPoints(@RequestBody(required = true) RewardRequest request) {
		try {
		return ResponseEntity.ok(rewardPointsService.calculateRewardPoints(request));
	}catch(Exception e){
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	}
	/**
	 * This method saves transaction details into database
	 */
	@RequestMapping(value="v1/saveTransaction",method = RequestMethod.POST)
	public ResponseEntity<String> addingTransactionDetails(@RequestBody TransactionRequest transactionRequest) {
		try{
			return ResponseEntity.ok(rewardPointsService.addTransactionDetails(transactionRequest));
		}catch(IllegalArgumentException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		catch(Exception e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	/**
	 * This method fetches all transaction details stored in database
	 */
	@RequestMapping(value="v1/getTransactions",method = RequestMethod.GET)
	public ResponseEntity<?> getTransactionDetails() {
		try{
		return ResponseEntity.ok(rewardPointsService.fetchAllTransactions());
	}catch(Exception e){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
				body(e.getMessage());
	}
	}
	
}
