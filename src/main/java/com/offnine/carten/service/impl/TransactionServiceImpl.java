package com.offnine.carten.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offnine.carten.Repo.SellerRepo;
import com.offnine.carten.Repo.TransactionRepo;
import com.offnine.carten.modal.Order;
import com.offnine.carten.modal.Seller;
import com.offnine.carten.modal.Transaction;
import com.offnine.carten.service.TransactionService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class TransactionServiceImpl  implements TransactionService{
    @Autowired
 private  TransactionRepo transactionRepo;
 @Autowired
 private SellerRepo sellerRepo;
    @Override
    public Transaction createTransaction(Order order) {
       Seller seller = sellerRepo.findById(order.getSellerId()).get();
        Transaction transaction = new Transaction();
        transaction.setSeller(seller);
        transaction.setCustomer(order.getUser());
        transaction.setOrder(order);
                return transactionRepo.save(transaction);

    }

    @Override
    public List<Transaction> getTransactionBySeller(Seller seller) {
        return transactionRepo.findBySellerId(seller.getId());
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    }
