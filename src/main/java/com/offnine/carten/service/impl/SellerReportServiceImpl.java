package com.offnine.carten.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offnine.carten.Repo.SellerReportRepo;
import com.offnine.carten.modal.Seller;
import com.offnine.carten.modal.SellerReport;
import com.offnine.carten.service.SellerReportService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SellerReportServiceImpl implements SellerReportService {
 @Autowired
 private SellerReportRepo sellerReportRepo;
    @Override
    public SellerReport getSellerReport(Seller seller) {
        SellerReport sr = sellerReportRepo.findBySellerId(seller.getId());
        if(sr==null){
            SellerReport newReport = new SellerReport();
            newReport.setSeller(seller);
            return sellerReportRepo.save(newReport);

        }

                return sr;

    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {
       return sellerReportRepo.save(sellerReport);
    }

  
    
}
