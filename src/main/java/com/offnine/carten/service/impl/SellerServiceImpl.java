package com.offnine.carten.service.impl;
import com.offnine.carten.service.SellerService;

import lombok.RequiredArgsConstructor;

import com.offnine.carten.Repo.AddressRepo;
import com.offnine.carten.Repo.SellerRepo;
import com.offnine.carten.config.JwtProvider;
import com.offnine.carten.domain.AccountStatus;
import com.offnine.carten.domain.USER_ROLE;
import com.offnine.carten.exception.SellerException;
import com.offnine.carten.modal.Address;
import com.offnine.carten.modal.Seller;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public  class SellerServiceImpl implements SellerService {
	public final SellerRepo sellerRepo;
	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;
	private final AddressRepo addressRepo;

	@Override
	 public Seller getSellerProfile(String jwt) throws Exception {
		String email = jwtProvider.getEmailFromJwtToken(jwt);
		return this.getSellerByEmail(email);

	}

	@Override
	public Seller createSeller(Seller seller) throws Exception {
		Seller sellerExist = sellerRepo.findByEmail(seller.getEmail());
		if(sellerExist!=null){
			throw new Exception("Seller already exist,used different email id" +sellerExist.getEmail());
		}
		Address savedAddress = addressRepo.save(seller.getPickupAddres());
		Seller newSeller = new Seller();
		newSeller.setEmail(seller.getEmail());
		newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
		newSeller.setSellerName(seller.getSellerName());
		newSeller.setPickupAddres(savedAddress);
		// newSeller.setAccountStatus(AccountStatus.ACTIVE);
		newSeller.setBankDetails(seller.getBankDetails());
		newSeller.setBusinessDetails(seller.getBusinessDetails());
		newSeller.setRole(USER_ROLE.ROLE_SELLER);
		newSeller.setMobile(seller.getMobile());
		newSeller.setGSTIN(seller.getGSTIN());
		sellerRepo.save(newSeller);
		
		return newSeller;
	}

	@Override
	public Seller getSellerById(Long id) throws SellerException {
		return sellerRepo.findById(id).orElseThrow(()-> new SellerException("Seller not found with id" +id));
	}

	@Override
	public Seller getSellerByEmail(String email) throws Exception {
		Seller seller = sellerRepo.findByEmail(email);
		if(seller==null){
			throw new Exception("Seller not found....");
		}
				return seller;
	}

	@Override
	public List<Seller> getAllSellers(AccountStatus status) {
		return sellerRepo.findByAccountStatus(status);
	}

	@Override
	public Seller updateSeller(Long Id, Seller seller) throws Exception {
		System.out.println("Seller servuice upside");
		Seller existingSeller = this.getSellerById(Id);
		if(seller.getSellerName() != null){
			existingSeller.setSellerName(seller.getSellerName());

		}
		if(seller.getMobile() != null){
			existingSeller.setMobile(seller.getMobile());
		}
		if(seller.getEmail() != null){
			existingSeller.setEmail(seller.getEmail());
		}
		if(seller.getBusinessDetails()!= null && seller.getBusinessDetails().getBusinessName() !=null){
			existingSeller.getBusinessDetails().setBusinessName(seller.getBusinessDetails().getBusinessName());
		}
		if(seller.getBankDetails() != null
		&& seller.getBankDetails().getAccountHolderName() !=null
		&& seller.getBankDetails().getIfscCode() !=null
		&& seller.getBankDetails().getAccountNumber() !=null
		
		
		
		){
			existingSeller.getBankDetails().setAccountHolderName(seller.getBankDetails().getAccountHolderName());
			existingSeller.getBankDetails().setAccountNumber(seller.getBankDetails().getAccountNumber());
			existingSeller.getBankDetails().setIfscCode(seller.getBankDetails().getIfscCode());
		}
		if(seller.getPickupAddres() != null
		&&seller.getPickupAddres().getAddress() !=null
		&&seller.getPickupAddres().getCity() !=null
		&&seller.getPickupAddres().getState() !=null

		
		
		){


			existingSeller.getPickupAddres().setAddress(seller.getPickupAddres().getAddress());
			existingSeller.getPickupAddres().setCity(seller.getPickupAddres().getCity());
			existingSeller.getPickupAddres().setState(seller.getPickupAddres().getState());
			existingSeller.getPickupAddres().setMobile(seller.getPickupAddres().getMobile());
			existingSeller.getPickupAddres().setPinCode(seller.getPickupAddres().getPinCode());
		}
		if(seller.getGSTIN()!= null){
			existingSeller.setGSTIN(seller.getGSTIN());
		}
		if (seller.getEmail() != null) {
			existingSeller.setEmail(seller.getEmail());
		}
		sellerRepo.save(existingSeller);
		System.out.println("Seller servuice down");
		return existingSeller;
	}

	@Override
	public void deleteSeller(Long id) throws Exception {
		Seller seller = getSellerById(id);
		sellerRepo.delete(seller);
	}

	@Override
	public Seller verifyEmail(String email, String otp) throws Exception {
		Seller seller =getSellerByEmail(email);
		seller.setEmailVerified(true);
		return sellerRepo.save(seller);
	}

	@Override
	public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception {
		Seller seller =getSellerById(sellerId);
		seller.setAccountStatus(status);
		return sellerRepo.save(seller);
	}

}
