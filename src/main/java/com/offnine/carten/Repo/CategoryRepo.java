package com.offnine.carten.Repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.Category;



public interface CategoryRepo  extends JpaRepository<Category,Long>{
  Category findByCategoryId(String categoryId);


}
