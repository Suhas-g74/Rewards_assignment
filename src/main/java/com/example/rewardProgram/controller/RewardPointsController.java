package com.example.rewardProgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * @throws Exception 
	 */
	@RequestMapping(value="v1/getRewardPoints",method = RequestMethod.GET)
	public ResponseEntity<?>  calculateRewardPoints(@RequestBody(required = true) RewardRequest request) throws Exception {
		return ResponseEntity.ok(rewardPointsService.calculateRewardPoints(request));
	}
	/**
	 * This method saves transaction details into database
	 * @throws Exception 
	 */
	@RequestMapping(value="v1/saveTransaction",method = RequestMethod.POST)
	public ResponseEntity<String> addingTransactionDetails(@RequestBody TransactionRequest transactionRequest) throws Exception {
		return ResponseEntity.ok(rewardPointsService.addTransactionDetails(transactionRequest));
	}
	/**
	 * This method fetches all transaction details for particular customer stored in database
	 */
	@RequestMapping(value="v1/getTransactions",method = RequestMethod.GET)
	public ResponseEntity<?> getTransactionDetails(@RequestParam("customerName") String  custName ) {
		return ResponseEntity.ok(rewardPointsService.fetchAllTransactions(custName));
	}
	/**
	 * This method handles all illegal argument exceptions
	 */
	 @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<String> handleCustomException(IllegalArgumentException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	 /**
		 * This method to handle all run time exceptions
		 */ 
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleUnexpectedException(Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
