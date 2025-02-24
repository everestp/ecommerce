package com.offnine.carten.service;

import com.offnine.carten.modal.Seller;
import com.offnine.carten.modal.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);
    
}
