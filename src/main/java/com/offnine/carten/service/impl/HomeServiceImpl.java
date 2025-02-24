package com.offnine.carten.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.offnine.carten.Repo.DealRepo;
import com.offnine.carten.domain.HomeCategorySection;
import com.offnine.carten.modal.Deal;
import com.offnine.carten.modal.Home;
import com.offnine.carten.modal.HomeCategory;
import com.offnine.carten.service.HomeService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class HomeServiceImpl implements HomeService{
    private final DealRepo dealRepo;

    @Override
    public Home createHomePageData(List<HomeCategory> allCategories) {

        List<HomeCategory> girdCategories =allCategories.stream()
        .filter(category -> category.getSection() == HomeCategorySection.GRID)
        .collect(Collectors.toList());

        List<HomeCategory> shopByCategories =allCategories.stream()
        .filter(category -> category.getSection() == HomeCategorySection.SHOP_BY_CATEGORIES)
        .collect(Collectors.toList());

        List<HomeCategory> electricCategories =allCategories.stream()
        .filter(category -> category.getSection() == HomeCategorySection.ELECTRIC_CATEGORY)
        .collect(Collectors.toList());

        List<HomeCategory> dealCategories =allCategories.stream()
        .filter(category -> category.getSection() == HomeCategorySection.DEALS)
        .collect(Collectors.toList());
        

        List<Deal> createdDeals = new ArrayList<>();

        if(dealRepo.findAll().isEmpty()){
            List<Deal> deals =allCategories.stream()
            .filter(category -> category.getSection() == HomeCategorySection.DEALS)
        .map(category -> new Deal(null,10,category)).collect(Collectors.toList());
        }

        else createdDeals = dealRepo.findAll();
        

        Home home = new Home();
        home.setGrid(girdCategories);
        home.setShopByCategories(shopByCategories);
        home.setElectricCategories(electricCategories);
        home.setDeals(createdDeals);
        home.setDealCategories(dealCategories);

        return home;


        

       
    }
    
}
