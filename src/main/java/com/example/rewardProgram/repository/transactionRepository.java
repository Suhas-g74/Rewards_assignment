package com.example.rewardProgram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.rewardProgram.entity.Transactions;

@Repository
@Transactional
public interface transactionRepository extends JpaRepository<Transactions, Integer> {

}
