package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.User;



public interface UserRepo extends JpaRepository<User,Long>{
    User findByEmail(String email);
    
}
