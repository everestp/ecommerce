package com.offnine.carten.request;

import java.util.List;

import lombok.Data;


@Data
public class CreateReviewRequest {
    
    private String reviewText;
    private double ReviewRating;
    private List<String> productImage;
    
}
