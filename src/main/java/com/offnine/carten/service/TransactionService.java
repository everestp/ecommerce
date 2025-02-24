package com.offnine.carten.service;

import java.util.List;

import com.offnine.carten.modal.Order;
import com.offnine.carten.modal.Seller;
import com.offnine.carten.modal.Transaction;

public interface TransactionService{
    Transaction createTransaction(Order order);
    List<Transaction> getTransactionBySeller(Seller seller);
    List<Transaction> getAllTransactions();
    
}
