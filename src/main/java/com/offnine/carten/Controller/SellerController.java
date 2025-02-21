package com.offnine.carten.Controller;

import java.nio.channels.Selector;
import java.rmi.ServerException;

import org.apache.catalina.connector.Response;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import com.offnine.carten.Repo.VerificationCodeRepo;
import com.offnine.carten.Utils.OtpUtil;
import com.offnine.carten.config.JwtProvider;
import com.offnine.carten.domain.AccountStatus;
import com.offnine.carten.exception.SellerException;
import com.offnine.carten.modal.Seller;
import com.offnine.carten.modal.SellerReport;
import com.offnine.carten.modal.VerificationCode;
import com.offnine.carten.reponse.ApiResponse;
import com.offnine.carten.reponse.AuthResponse;
import com.offnine.carten.request.LoginRequest;
import com.offnine.carten.service.AuthService;
import com.offnine.carten.service.EmailService;
import com.offnine.carten.service.SellerService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepo  verificationCodeRepo;
    private final AuthService authService;
    @Autowired
    private  OtpUtil otpUtil;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;

@PostMapping("/login")
ResponseEntity<AuthResponse> loginSeller(
    @RequestBody LoginRequest req
) throws Exception{
String otp = req.getOtp();
String email =req.getEmail();

req.setEmail("seller_" + email);
AuthResponse authResponse =authService.signing(req);
return ResponseEntity.ok(authResponse);

}




@PatchMapping("/verify/{otp}")
public ResponseEntity<Seller> verifySellerEmail(@PathVariable String otp ) throws Exception{

    VerificationCode verificationCode =verificationCodeRepo.findByOtp(otp);
    if(verificationCode ==null || !verificationCode.getOtp().equals(otp)){
        throw new Exception("Wrong otp");
    }
       Seller seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);
       return new ResponseEntity<>(seller,HttpStatus.OK);


}
@PostMapping()
public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception {
    Seller savedSeller = sellerService.createSeller(seller);
    String otp = OtpUtil.generateOtp();
   
    VerificationCode verificationCode = new VerificationCode();
    verificationCode.setOtp(otp);
    verificationCode.setEmail(seller.getEmail());
     verificationCodeRepo.save(verificationCode);

     String subject = "Carten -Email Verification Code";
     String text = "Welcome to Carten  "+ seller.getSellerName()+"    , verify your account using this link ";
     String frontend_url ="http://localhost:5454/sellers/verify/"+otp;
     emailService.sendVerficationOtpEmail(seller.getEmail(), verificationCode.getOtp(), subject, text + frontend_url);

    
    return new ResponseEntity<>(savedSeller,HttpStatus.CREATED);

}
@GetMapping("/{id}")
public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException{
   Seller seller = sellerService.getSellerById(id);
   return new ResponseEntity<>(seller,HttpStatus.OK);
}
@GetMapping("/profile")
public ResponseEntity<Seller> getSellerByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
   
    Seller seller =sellerService.getSellerProfile(jwt);
    return new  ResponseEntity<>(seller,HttpStatus.OK);

}
// @GetMapping("/report")
// public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwt) throws Exception {
//     String email = jwtProvider.getEmailFromJwtToken(jwt);
//     Seller seller = sellerService.getSellerByEmail(email);
//     SellerReport report = sellerReportService.getSellerReport(seller);
//     return new ResponseEntity<>(report,HttpStatus.OK);
   
// }

@GetMapping("path")
public ResponseEntity<List<Seller>> getAllSellers(@RequestParam(required = false) AccountStatus status) {
   List<Seller> sellers = sellerService.getAllSellers(status);
   return ResponseEntity.ok(sellers);

   
}
@PatchMapping()
public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwt, @RequestBody Seller seller) throws Exception{
    Seller profile = sellerService.getSellerProfile(jwt);
    Seller updatedSeller = sellerService.updateSeller(profile.getId(), seller);
    return ResponseEntity.ok(updatedSeller);
    
}
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws Exception{
    
    return ResponseEntity.noContent().build();
}

}
       