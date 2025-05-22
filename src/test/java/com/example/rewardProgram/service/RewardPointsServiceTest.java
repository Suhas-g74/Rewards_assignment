package com.example.rewardProgram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.rewardProgram.entity.Transactions;
import com.example.rewardProgram.model.RewardRequest;
import com.example.rewardProgram.model.RewardResponse;
import com.example.rewardProgram.model.TransactionRequest;
import com.example.rewardProgram.repository.transactionRepository;


@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class RewardPointsServiceTest {
	
	@InjectMocks
	RewardPointsService rewardPointsService;
	
	@Mock
	private transactionRepository transRepository;
	
	@Mock
	private RewardResponse rewardResponse;
	
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test 
	public void calculatePoints_test() {
		rewardPointsService = new RewardPointsService();
		assertEquals(90, rewardPointsService.calculatePoints(120.0));	
	}
	@Test 
	public void calculatePointsbetween50and100_test() {
		rewardPointsService = new RewardPointsService();
		assertEquals(7, rewardPointsService.calculatePoints(57.0));	
	}
	@Test 
	public void calculatePointsfor50_test() {
		rewardPointsService = new RewardPointsService();
		assertEquals(0, rewardPointsService.calculatePoints(50.0));		
	}
	@Test 
	public void calculatePointsfor100_test() {
		rewardPointsService = new RewardPointsService();
		assertEquals(50, rewardPointsService.calculatePoints(100.0));	
	}
	@Test
	public void testSaveMethod() throws Exception {
		Transactions savedRequest= new Transactions();
		LocalDate testDate= LocalDate.of(2025, 04, 03);
		savedRequest.setAmount(20.0);
		savedRequest.setUsername("Mike");
		savedRequest.setTransactionDate(testDate);
		TransactionRequest transactionRequest= new TransactionRequest();
		transactionRequest.setAmount(20.0);
		transactionRequest.setUserName("mike");
		transactionRequest.setTransactionDate("2025-04-03");
		when(transRepository.saveAndFlush(savedRequest)).thenReturn(savedRequest);
		String result = rewardPointsService.addTransactionDetails(transactionRequest);

		assertEquals("Transaction added succssfully", result);
     }
	@Test
	public void fetchAllTransactions_test() throws Exception {
		Transactions firstRequest= new Transactions();
		LocalDate testDate= LocalDate.of(2025, 04, 03);
		firstRequest.setAmount(20.0);
		firstRequest.setUsername("Mike");
		firstRequest.setTransactionDate(testDate);
		List<Transactions> dbResult= new ArrayList<>();
		dbResult.add(firstRequest);
		when(transRepository.findByUsername(any())).thenReturn(dbResult);
		List<Transactions> result = rewardPointsService.fetchAllTransactions("Mike");

		assertEquals(20.0, result.get(0).getAmount() );
     }
	
	@Test
	public void calculateTotalRewardPoints_test() throws Exception {
		RewardRequest request= new RewardRequest();
		request.setCustomerName("Mike");
		request.setNoOfMonths(3);
		Transactions firstRequest= new Transactions();
		LocalDate testDate= LocalDate.of(2025, 04, 13);
		firstRequest.setAmount(120.0);
		firstRequest.setUsername("Mike");
		firstRequest.setTransactionDate(testDate);
		Transactions secondRequest= new Transactions();
		LocalDate testDate2= LocalDate.of(2025, 04, 15);
		secondRequest.setAmount(102.0);
		secondRequest.setUsername("Mike");
		secondRequest.setTransactionDate(testDate2);
		List<Transactions> dbResult= new ArrayList<>();
		dbResult.add(firstRequest);
		dbResult.add(secondRequest);
		when(transRepository.findByUsername(any())).thenReturn(dbResult);
		rewardResponse=rewardPointsService.calculateRewardPoints(request);

		verify(rewardResponse,times(1)).setTotalRewardPoints(144);
     }
}
