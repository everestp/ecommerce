package com.offnine.carten.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.offnine.carten.Repo.UserRepo;
import com.offnine.carten.domain.USER_ROLE;
import com.offnine.carten.modal.User;
import com.offnine.carten.modal.VerificationCode;
import com.offnine.carten.reponse.ApiResponse;
import com.offnine.carten.reponse.AuthResponse;
import com.offnine.carten.reponse.SignUpRequest;
import com.offnine.carten.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

@Autowired
private UserRepo userRepo;
@Autowired
private AuthService authService;



    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignUpRequest req)  throws Exception{

   String jwt = authService.createUser(req);
   AuthResponse res = new AuthResponse();
   res.setJwt(jwt);
   res.setMessege("User Registered Sucessfull");
   res.setRole(USER_ROLE.ROLE_CUSTOMER);


            return ResponseEntity.ok(res);
        
        
    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody VerificationCode req)  throws Exception{

  authService.sentLoginOtp(req.getEmail());
ApiResponse res = new ApiResponse();
res.setMessage("otp sent sucessfully");
            return ResponseEntity.ok(res);


    }
}    
        
    

