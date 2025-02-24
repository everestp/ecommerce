package com.offnine.carten.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offnine.carten.Repo.HomeCategoryRepo;
import com.offnine.carten.modal.HomeCategory;
import com.offnine.carten.service.HomeCategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HomeCategoryServiceImpl implements HomeCategoryService{

    @Autowired
    private HomeCategoryRepo homeCategoryRepo;

    
    @Override
    public HomeCategory createHomeCategory(HomeCategory homeCategory) {
        return homeCategoryRepo.save(homeCategory);
    }

    @Override
    public List<HomeCategory> createCategories(List<HomeCategory> homeCategories) {
       if(homeCategoryRepo.findAll().isEmpty()){
        return  homeCategoryRepo.saveAll(homeCategories);
       }
       return homeCategoryRepo.findAll();
    }

    @Override
    public List<HomeCategory> getAllHomeCategories() {
       return homeCategoryRepo.findAll();
    }



    @Override
    public HomeCategory updateHomeCategory(HomeCategory category, Long id) throws Exception {
      HomeCategory existingCategory = homeCategoryRepo.findById(id).orElseThrow(()-> new Exception("Category Not found"));
      if(category.getImage() !=null){
        existingCategory.setImage(category.getImage());

      }
      if(category.getCategoryId() !=null){
        existingCategory.setImage(category.getImage());
      }
      return homeCategoryRepo.save(existingCategory);
    }

    
    
}
