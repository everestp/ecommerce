package com.offnine.carten.service;

import java.util.List;

import com.offnine.carten.modal.Home;
import com.offnine.carten.modal.HomeCategory;

public interface HomeService {
    public Home createHomePageData(List<HomeCategory> allCategories);


    
}
