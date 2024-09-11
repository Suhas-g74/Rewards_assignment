package com.example.rewardProgram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.rewardProgram.repository.transactionRepository;


@RunWith(MockitoJUnitRunner.class)
public class RewardPointsServiceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	RewardPointsService rewardPointsService;
	
	@Mock
	transactionRepository transRepository;
	
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(rewardPointsService).build();
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
}
