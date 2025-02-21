package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.Address;

public interface AddressRepo extends JpaRepository<Address,Long> {
    
}
