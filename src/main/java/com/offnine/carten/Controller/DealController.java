package com.offnine.carten.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.offnine.carten.modal.Deal;
import com.offnine.carten.reponse.ApiResponse;
import com.offnine.carten.service.DealService;
import com.offnine.carten.service.impl.DealServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/deals")
public class DealController {
  @Autowired
  private DealService dealService;
@PostMapping()
public ResponseEntity<Deal> createDeal(
    @RequestBody Deal deals
) {
    Deal createdDeal = dealService.createDeal(deals);
    return new ResponseEntity<>(createdDeal,HttpStatus.ACCEPTED);
 

}
@PatchMapping("/{id}")
public ResponseEntity<Deal> updateDeal(
    @PathVariable Long id,
    @RequestBody Deal deal
) throws Exception{
 Deal updatedDeal = dealService.updateDeal(deal, id);
 return ResponseEntity.ok(updatedDeal);
}

@DeleteMapping("/{id}")
public ResponseEntity<ApiResponse> deleteDeal(
    @PathVariable Long id
   
) throws Exception{
  dealService.deleteDeal(id);
  ApiResponse res= new ApiResponse();
  res.setMessage("Deal deleted");
  res.
  
return  new ResponseEntity<>(res,HttpStatus.ACCEPTED);

}





    
}
