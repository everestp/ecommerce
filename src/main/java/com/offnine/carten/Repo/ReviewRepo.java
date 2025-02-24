package com.offnine.carten.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.offnine.carten.modal.Review;

public interface ReviewRepo extends JpaRepository<Review,Long> {
    List<Review> findByProductId(Long productId);
    
}
