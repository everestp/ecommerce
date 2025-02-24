package com.offnine.carten.service;

import java.util.List;

import com.offnine.carten.modal.Home;
import com.offnine.carten.modal.HomeCategory;

public interface HomeCategoryService {
    HomeCategory createHomeCategory(HomeCategory homeCategory);
    List<HomeCategory> createCategories(List<HomeCategory> homeCategories);
    HomeCategory updateHomeCategory(HomeCategory homeCategory,Long id) throws Exception;
    List<HomeCategory> getAllHomeCategories();

}
