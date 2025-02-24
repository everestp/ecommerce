package com.offnine.carten.service;

import java.util.List;


import org.springframework.data.domain.Page;


import com.offnine.carten.exception.ProductException;
import com.offnine.carten.modal.Product;
import com.offnine.carten.modal.Seller;
import com.offnine.carten.request.CreateProductRequest;


public interface ProductService {
   public Product createProduct(CreateProductRequest req,Seller seller);
   public void deleteProduct(Long productId) throws ProductException;
   public Product updateProduct( Long productId,Product product) throws ProductException;
   public Product findProductById(Long productId) throws ProductException;
   List<Product> searchProduct(String query);
    public Page<Product> getAllProduct(
        String category,
        String brand,
        String colors,
        String sizes,
        Integer minPrice,
        Integer maxPrice,
        Integer minDiscount,
        String sort,
        String stock,
        Integer pageNUmber

    );
 
List<Product> getProductsBySellerId(Long sellerId);


   
}
