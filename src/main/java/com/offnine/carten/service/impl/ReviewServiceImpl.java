package com.offnine.carten.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.offnine.carten.Repo.ReviewRepo;
import com.offnine.carten.modal.Product;
import com.offnine.carten.modal.Review;
import com.offnine.carten.modal.User;
import com.offnine.carten.request.CreateReviewRequest;
import com.offnine.carten.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ReviewServiceImpl  implements ReviewService{


    private final ReviewRepo reviewRepo;
   

    @Override
    public Review createReview(CreateReviewRequest req, User user,Product product) {
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReviewText(req.getReviewText());
        review.setProductImages(req.getProductImage());
        review.setRating(req.getReviewRating());
        product.getReviews().add(review);
        return reviewRepo.save(review);

    }

    @Override
    public List<Review> getReviewByProductId(Long productId) {
       return reviewRepo.findByProductId(productId);

    }

    @Override
    public Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception {
        Review review = getReviewById(reviewId);
        if(review.getUser().getId().equals(userId)){
            review.setReviewText(reviewText);
            review.setRating(rating);
            return reviewRepo.save(review);
        }

        throw new Exception("You cannot update this review");
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {
       Review review = getReviewById(reviewId);
       if(review.getUser().getId().equals(userId)){
        reviewRepo.delete(review);
       }
      else{
        throw new Exception("You cannot delete this review");
      }
    }

    @Override
    public Review getReviewById(Long reviewId) throws Exception {
       return reviewRepo.findById(reviewId).orElseThrow(()-> new Exception("Review not found"));
    }
    
}
