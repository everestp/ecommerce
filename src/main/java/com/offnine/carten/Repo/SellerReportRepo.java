package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.SellerReport;

public interface SellerReportRepo extends JpaRepository<SellerReport,Long>{

SellerReport  findBySellerId(Long sellerId)
    
} 
