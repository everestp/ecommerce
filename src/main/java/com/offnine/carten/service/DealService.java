package com.offnine.carten.service;



import java.util.List;

import com.offnine.carten.modal.Deal;

public interface DealService  {
    List<Deal> getDeals();
    Deal createDeal(Deal deal) throws Exception;
    Deal updateDeal(Deal deal,Long id) throws Exception;
    void deleteDeal(Long id) throws Exception;
    
}
