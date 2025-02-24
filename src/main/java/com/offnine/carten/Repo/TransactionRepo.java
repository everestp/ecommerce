package com.offnine.carten.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction ,Long> {
    List<Transaction> findBySellerId(Long sellerId);
    
}
