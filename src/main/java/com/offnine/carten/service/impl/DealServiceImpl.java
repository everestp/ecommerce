package com.offnine.carten.service.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offnine.carten.Repo.DealRepo;
import com.offnine.carten.Repo.HomeCategoryRepo;
import com.offnine.carten.modal.Deal;
import com.offnine.carten.modal.HomeCategory;
import com.offnine.carten.service.DealService;
import com.offnine.carten.service.HomeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DealServiceImpl  implements DealService{
    @Autowired
    private DealRepo dealRepo;

    @Autowired
    private HomeCategoryRepo homeCategoryRepo;

    @Override
    public List<Deal> getDeals() {
        return dealRepo.findAll();
    }

    @Override
    public Deal createDeal(Deal deal) throws Exception {
        HomeCategory category = homeCategoryRepo.findById(deal.getCategory().getId()).orElseThrow(()-> new Exception("Deal not  found"));
       Deal neWDeal = dealRepo.save(deal);
       neWDeal.setCategory(category);
       neWDeal.setDiscount(deal.getDiscount());

       return dealRepo.save(neWDeal);
    }

    @Override
    public Deal updateDeal(Deal deal,Long id) throws Exception {
       Deal existingDeal = dealRepo.findById(id).orElse(null);
       HomeCategory category = homeCategoryRepo.findById(deal.getCategory().getId()).orElse(null);
       if(existingDeal !=null){
        if(deal.getDiscount() != null){
            existingDeal.setDiscount(deal.getDiscount());
        }
        if(category !=null){
            existingDeal.setCategory(category);
        }
        return dealRepo.save(existingDeal);
       }
       throw new Exception("Deal not found");
    }

    @Override
    public void deleteDeal(Long id) throws Exception {
        Deal deal = dealRepo.findById(id).orElseThrow(()-> new Exception("Deal not found"));
        dealRepo.delete(deal);
        

        
    }
    
}
