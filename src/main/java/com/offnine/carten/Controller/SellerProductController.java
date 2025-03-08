package com.offnine.carten.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;

import com.offnine.carten.exception.ProductException;
import com.offnine.carten.exception.SellerException;
import com.offnine.carten.modal.Product;
import com.offnine.carten.modal.Seller;
import com.offnine.carten.request.CreateProductRequest;
import com.offnine.carten.service.ProductService;
import com.offnine.carten.service.SellerService;

import lombok.RequiredArgsConstructor;

import java.lang.module.ResolutionException;
import java.rmi.ServerException;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("sellers/products")
public class SellerProductController {

    private final SellerService sellerService;
    private final ProductService productService;
    @GetMapping()
    public ResponseEntity<List<Product>> getProductBySellerId(@RequestHeader("Authorization") String jwt) throws ProductException,SellerException {
        Seller seller = sellerService.getSellerProfile(jwt);
        List<Product> products = productService.getProductsBySellerId(seller.getId());
        return new ResponseEntity<>(products,HttpStatus.OK);
      
    }
    @PostMapping()
    public ResponseEntity<Product> createProduct(
        @RequestBody CreateProductRequest req,
        @RequestHeader("Authorization")String jwt
    )throws Exception{
Seller seller = sellerService.getSellerProfile(jwt);
Product product =productService.createProduct(req, seller);
return new ResponseEntity<>(product,HttpStatus.CREATED);

    }

    @DeleteMapping("/{productId}")
   public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
    try {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    } catch (ProductException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
   }

@PutMapping("/{productId}")
public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
   try {
    Product updateProduct = productService.updateProduct(productId, product);
    return new ResponseEntity<>(updateProduct,HttpStatus.OK);
    
   } catch (ProductException e) {
  
   }
  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}


}
