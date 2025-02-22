package com.offnine.carten.modal;

import java.util.List;

import lombok.Data;


@Data   // it is not a entity it just a class
public class Home {

    private List<HomeCategory> grid;

    private List<HomeCategory> shopByCategories;

    private List<HomeCategory> electricCategories;

    private List<HomeCategory> dealCategories;

    private List<Deal> deals;

    

    
    
}
