package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.HomeCategory;

public interface HomeCategoryRepo  extends JpaRepository<HomeCategory,Long>{
    
    
}
