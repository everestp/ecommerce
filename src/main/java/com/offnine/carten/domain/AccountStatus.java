package com.offnine.carten.domain;

public enum AccountStatus {
PENDING_VERRIFICATION ,  //Account is created but not yet verified
ACTIVE, // Account is active and is good for standing
SUSPENDED,  //Account is tempeoralry suspended ,possible due to violation
DEACTIVATED, //Account is Deactivated user may have chosen to deactivate it,
BANNED,    //Account is permantly banned due to severe violations
CLOSED   //Account id perm,antly closed ,possibly at user request
}
