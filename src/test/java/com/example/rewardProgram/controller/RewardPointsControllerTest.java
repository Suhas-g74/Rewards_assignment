package com.example.rewardProgram.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.rewardProgram.entity.Transactions;
import com.example.rewardProgram.model.RewardRequest;
import com.example.rewardProgram.model.TransactionRequest;
import com.example.rewardProgram.service.RewardPointsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class RewardPointsControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private RewardPointsService rewardPointsService;

	@InjectMocks
	private RewardPointsController rewardPointsController;

	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(rewardPointsController).build();
	}

	
	@Test
	public void addingTransactionDetails_Test() throws Exception {
		TransactionRequest transactionRequest= new TransactionRequest();
		
		 
		transactionRequest.setAmount(20.0);
		transactionRequest.setUserName("mike");
		transactionRequest.setTransactionDate("2024-08-03");

		MvcResult result = mockMvc.perform(post("/api/v1/saveTransaction")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(transactionRequest)))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		
		assertEquals(200, result.getResponse().getStatus());
	}

	
	private String asJsonString(Object obj) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	
	
	@Test
	public void getTransactionDetails_Test() throws Exception{
		Transactions transaction1 = new Transactions();
		Transactions transaction2 = new Transactions();
	
		transaction1.setAmount(120.0);
		transaction1.setUsername("mike");
		transaction1.setId(1);
		
		transaction2.setAmount(120.0);
		transaction2.setUsername("mike");
		transaction2.setId(2);
		List<Transactions> mockResponse = Arrays.asList(transaction1, transaction2);

		when(rewardPointsService.fetchAllTransactions())
				.thenReturn(mockResponse);

		String expectedResponse = new ObjectMapper().writeValueAsString(mockResponse);

		MvcResult result = mockMvc.perform(get("/api/v1/getTransactions")).
		andExpect(status().isOk()).
		andReturn();
		assertEquals(expectedResponse,result.getResponse().getContentAsString());
	}
	
	@Test
	public void calculateRewardPoints_Test() throws Exception {
		RewardRequest rewardrequest= new RewardRequest();
				rewardrequest.setCustomerName("mike");
		

		MvcResult result = mockMvc.perform(get("/api/v1/getRewardPoints")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(rewardrequest)))
				.andExpect(status().isOk())
				.andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}
}
