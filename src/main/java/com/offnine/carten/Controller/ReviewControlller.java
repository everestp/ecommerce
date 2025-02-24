package com.offnine.carten.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.offnine.carten.modal.Product;
import com.offnine.carten.modal.Review;
import com.offnine.carten.modal.User;
import com.offnine.carten.reponse.ApiResponse;
import com.offnine.carten.request.CreateReviewRequest;
import com.offnine.carten.service.ProductService;
import com.offnine.carten.service.ReviewService;
import com.offnine.carten.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewControlller {
    
    private final ReviewService reviewService;
    private final UserService userService;
    private final ProductService productService;


    @GetMapping("/productd/{productId}/reviews")
    public ResponseEntity<List<Review>> getReviewByProductId(
        @PathVariable Long productId
    ){
        List<Review> review = reviewService.getReviewByProductId(productId);
        return ResponseEntity.ok(review);
    }
@PostMapping("/products/{productId}/reviews")
public ResponseEntity<Review> writeReview(
    @PathVariable Long productId,
    @RequestBody CreateReviewRequest req,
    @RequestHeader("Authorization") String jwt
) throws Exception{
User user = userService.findUserByJwtToken(jwt);
Product product = productService.findProductById(productId);
Review review = reviewService.createReview(req, user, product);

return ResponseEntity.ok(review);
}



@PatchMapping("/reviews/{reviewId}")
public ResponseEntity<Review> updateReview (
    @PathVariable Long reviewId,
    @RequestBody CreateReviewRequest req,
    @RequestHeader("Authorization") String jwt
) throws Exception{
    User user = userService.findUserByJwtToken(jwt);
    Review review = reviewService.updateReview(reviewId, jwt, req.getReviewRating(), user.getId());
return ResponseEntity.ok(review);
}


@DeleteMapping("/reviews/{reviewId}")
public ResponseEntity<ApiResponse> deleteReview (
    @PathVariable Long reviewId,
    @RequestHeader("Authorization") String jwt
) throws Exception{
    User user =userService.findUserByJwtToken(jwt);
 reviewService.deleteReview(reviewId, user.getId());
 ApiResponse res = new ApiResponse(); 
 res.setMessage("Review Delete Sucessfully");
return ResponseEntity.ok(res);


}

}
