package com.offnine.carten.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.offnine.carten.reponse.ApiResponse;

@RestController
public class HomeController {
    @GetMapping
    public ApiResponse HomeControllerHandler(){
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setMessage("Welcome to ecommerce multi vendor system");
        return apiResponse;
    }
}
