package com.offnine.carten.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.offnine.carten.modal.Seller;
import com.offnine.carten.modal.Transaction;
import com.offnine.carten.service.SellerService;
import com.offnine.carten.service.TransactionService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final SellerService sellerService;


    @GetMapping("/seller")
    public ResponseEntity<List<Transaction>> getTransactionBySeller(
        @RequestHeader("Authorization") String jwt

    ) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwt);
        List<Transaction> transactions = transactionService.getTransactionBySeller(seller);
        return ResponseEntity.ok(transactions);
    }

@GetMapping()
public ResponseEntity<List<Transaction>> getAllTransaction(){
    List<Transaction> transactions = transactionService.getAllTransactions();
    return ResponseEntity.ok(transactions);
}

    
}
