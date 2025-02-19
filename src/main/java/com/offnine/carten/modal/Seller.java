package com.offnine.carten.modal;


import com.offnine.carten.domain.AccountStatus;
import com.offnine.carten.domain.USER_ROLE;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String sellerName;

    private String mobile;
    
    @Column(unique = true,nullable=false)
    private String email;

    private String password;

    @Embedded
    private BusinessDetails BusinessDetails = new BusinessDetails();

    @Embedded
    private BankDetails bankDetails= new BankDetails();

    //    @OneToMany(cascade = CascadeType.ALL)

    // private Address pickupAddres;

    private String GSTIN;


    private USER_ROLE role = USER_ROLE.ROLE_SELLER;

    private boolean isEmailVerified = false;

    private AccountStatus accountStatus = AccountStatus.PENDING_VERRIFICATION;





    
}
