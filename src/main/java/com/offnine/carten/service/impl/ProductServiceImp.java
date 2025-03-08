package com.offnine.carten.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.offnine.carten.Repo.CategoryRepo;
import com.offnine.carten.Repo.ProductRepo;
import com.offnine.carten.exception.ProductException;
import com.offnine.carten.modal.Category;
import com.offnine.carten.modal.Product;
import com.offnine.carten.modal.Seller;
import com.offnine.carten.request.CreateProductRequest;
import com.offnine.carten.service.ProductService;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;;


@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{
    @Autowired
  ProductRepo productRepo;
  @Autowired
 CategoryRepo categoryRepo;




    @Override
    public Product createProduct(CreateProductRequest req, Seller seller) {
        Category category1 = categoryRepo.findByCategoryId(req.getCategory());
        if(category1==null){
            Category category = new Category();
            category.setCategoryId(req.getCategory());
            category.setLevel(1);
            category1 = categoryRepo.save(category);

        }
        Category category2 = categoryRepo.findByCategoryId(req.getCatergory2());
        if(category2==null){
            Category category = new Category();
            category.setCategoryId(req.getCatergory2());
            category.setLevel(2);
            category.setParentCategory(category1);
            category2 = categoryRepo.save(category);

        }
        Category category3 = categoryRepo.findByCategoryId(req.getCategory3());
        if(category3==null){
            Category category = new Category();
            category.setCategoryId(req.getCategory3());
            category.setLevel(3);
            category.setParentCategory(category2);
            category3 = categoryRepo.save(category);

        }
        int discountPercentage = calculateDiscountPercentage(req.getMrpPrice(),req.getSellingPrice());
                Product product = new Product();
                product.setSeller(seller);
                product.setCategory(category3);
                product.setDescription(req.getDescription());
                product.setCreatedAt(LocalDateTime.now());
                product.setTitle(req.getTitle());
                product.setColor(req.getColor());
                product.setSellingPrice(req.getSellingPrice());
                product.setImages(req.getImages());
                product.setMrpPrice(req.getMrpPrice());
                product.setSizes(req.getSize());
                product.setDiscountPrice(discountPercentage);
                product.setQuantity(req.getQuantity());
        
        
                return productRepo.save(product);
            }
        
            private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
               if(mrpPrice <=0){
               return 0;
               }
               double discount = mrpPrice-sellingPrice;
               double discountPercentage =(discount/mrpPrice) *100;
               return (int) discountPercentage;
            }
        
            @Override
    public void deleteProduct(Long productId) throws ProductException {
       Product product = findProductById(productId);
       productRepo.delete(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        findProductById(productId);
       product.setId(productId);
       return productRepo.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        return productRepo.findById(productId).orElseThrow(()-> new ProductException("Product not found with id ="+productId));
    }

    @Override
    public List<Product> searchProduct(String query) {
       return productRepo.searchProduct(query);
    }

    public Page<Product> getAllProduct(String category, String brand, String colors, String sizes, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber) {
                Specification<Product> spec =(root,query,criteriaBuilder)->{
                                   List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();
                                   if(category!=null){
                                    Join<Product,Category> categoryJoin = root.join("category");
                                    predicates.add(criteriaBuilder.equal(categoryJoin.get("categoryId"), category));
                                   }
                                   
                                   if(colors!=null &&!colors.isEmpty()){
                                   System.out.println("Color ======>*********=====>"+colors);
                                   predicates.add(criteriaBuilder.equal(root.get("color"), colors));
                               
                                   }
                                   if(sizes!=null &&!sizes.isEmpty()){
                                    
                                    predicates.add(criteriaBuilder.equal(root.get("size"), sizes));
                                
                                    }

                                    if (minPrice != null) {
                                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"), minPrice));
                                    }
                    
                                    // Add maximum price filter if not null
                                    if (maxPrice != null) {
                                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"), maxPrice));
                                    }
                                        if(minDiscount!=null){
                                    
                                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPrice"), minDiscount));
                                        
                                            }
                                            if(stock!=null){
                                    
                                                predicates.add(criteriaBuilder.equal(root.get("stock"), stock));
                                            
                                                }
                                          return criteriaBuilder.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
                            };
                        
            Pageable pageable;
            
                if(sort!=null && !sort.isEmpty()){
                    switch(sort){
                        case "price_low":
                        pageable = PageRequest.of(pageNumber != null ? pageNumber : 0, 10,org.springframework.data.domain.Sort.by("sellingPrice").ascending());
                        break;
                        case "price_high":
                        pageable = PageRequest.of(pageNumber != null ? pageNumber : 0, 10,org.springframework.data.domain.Sort.by("sellingPrice").descending());
                        break;

                        default:
                        pageable = PageRequest.of(pageNumber != null ? pageNumber : 0, 10,org.springframework.data.domain.Sort.unsorted());
                        break;
                    }
                }
                else{
                    pageable =PageRequest.of(pageNumber !=null ? pageNumber:0, 10,org.springframework.data.domain.Sort.unsorted());
                }
       return productRepo.findAll( spec,pageable);
    }

    @Override
    public List<Product> getProductsBySellerId(Long sellerId) {
       return productRepo.findBySellerId(sellerId);
    }

    

   


    
}
