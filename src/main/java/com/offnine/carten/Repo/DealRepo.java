package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.Deal;
import java.util.List;
import java.util.Optional;


public interface DealRepo extends JpaRepository<Deal ,Long> {
    
}
