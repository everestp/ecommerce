package com.offnine.carten.service;

import java.util.List;

import com.offnine.carten.modal.Product;
import com.offnine.carten.modal.Review;
import com.offnine.carten.modal.User;
import com.offnine.carten.request.CreateReviewRequest;

public interface ReviewService {
    Review createReview(CreateReviewRequest req,User user,Product product);
    List<Review> getReviewByProductId(Long productId);
    Review updateReview(Long reviewId,String reviewText ,double rating,Long userId ) throws Exception;

    void deleteReview(Long reviewId,Long userId) throws Exception;
    Review getReviewById(Long reviewId) throws Exception;

}
