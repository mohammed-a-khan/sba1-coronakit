package com.iiht.evaluation.coronokit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.iiht.evaluation.coronokit.dao.KitDao;
import com.iiht.evaluation.coronokit.exception.ProductException;
import com.iiht.evaluation.coronokit.model.CoronaKit;
import com.iiht.evaluation.coronokit.model.KitDetail;
import com.iiht.evaluation.coronokit.model.KitItemDetails;

public class KitServiceImpl implements KitService {


	private boolean isValidPersonName(String productName) {
		return productName!=null && (productName.length()>=3 || productName.length()<=100);
	}
	
	 public static boolean isValidEmail(String pemail) 
	 { 
	     String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
	                         "[a-zA-Z0-9_+&*-]+)*@" + 
	                         "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
	                         "A-Z]{2,7}$"; 
	     Pattern pat = Pattern.compile(emailRegex); 
	     if (pemail == null) 
	         return false; 
	     return pat.matcher(pemail).matches(); 
	 }
	
	private boolean isValidDeliveryAddress(String deliveryAddress) {
		return deliveryAddress!=null && (deliveryAddress.length()>=1) && (!deliveryAddress.trim().equals(""));
	}
	
	private boolean isValidContactNumber(String contactNumber) {				
		return contactNumber!=null && contactNumber.matches("[1-9][0-9]{9}");
	}
	
	public boolean DeliveryAddress(CoronaKit coronaKit) throws ProductException {
		List<String> errMsgs = new ArrayList<>();
		boolean isValid=true;
		
		if(coronaKit!=null) {
			
			if(!isValidDeliveryAddress(coronaKit.getDeliveryAddress())) {
				isValid=false;
				errMsgs.add("Delivery Address can not left blank");
			}
			
			if(!errMsgs.isEmpty()) {
				throw new ProductException("Invalid Delivery Details: "+errMsgs);
			}
		}else {
			isValid=false;
			throw new ProductException("Delivery Details are not supplied");
		}
		
		return isValid;
	}
	
	public boolean isValidUser(CoronaKit coronaKit) throws ProductException {
		List<String> errMsgs = new ArrayList<>();
		boolean isValid=true;
		
		if(coronaKit!=null) {
			
			if(!isValidPersonName(coronaKit.getPersonName())) {
				isValid=false;
				errMsgs.add("User name can not left blank and must be of 3 to 20 in length");
			}
			if(!isValidEmail(coronaKit.getEmail())) {
				isValid=false;
				errMsgs.add("Email can not be left blank and must be a in valid format");
			}
			if(!isValidContactNumber(coronaKit.getContactNumber())) {
				isValid=false;
				errMsgs.add("Contact Number can not be left blank and must be of 10 digits only");
			}
			
			if(!errMsgs.isEmpty()) {
				throw new ProductException("Invalid User: "+errMsgs);
			}
		}else {
			isValid=false;
			throw new ProductException("User details are not supplied");
		}
		
		return isValid;
	}

	@Override
	public KitDetail validateAndAdd(KitDetail kitDetail, KitDao kitDAO) throws ProductException {
		kitDAO.add(kitDetail);
		return kitDetail;
	}

	@Override
	public KitDetail validateAndSave(KitDetail kitDetail, KitDao kitDAO) throws ProductException {
		kitDAO.save(kitDetail);
		return kitDetail;
	}

	@Override
	public boolean deleteKitItem(int kitId, KitDao kitDAO) throws ProductException {
		return kitDAO.deleteById(kitId);
	}

	@Override
	public List<KitItemDetails> getAllKitItems(int coronaKitId, KitDao kitDAO) throws ProductException {
		return kitDAO.getAll(coronaKitId);
	}

	@Override
	public CoronaKit validateAndAddCoronaKit(CoronaKit coronaKit, KitDao kitDAO) throws ProductException {
		if(isValidUser(coronaKit)) {
			kitDAO.addCoronaKit(coronaKit);
		}
		return coronaKit;
	}

	@Override
	public CoronaKit validateAndSaveCoronaKit(int coronaKitId, CoronaKit coronaKit, KitDao kitDAO) throws ProductException {
		if(DeliveryAddress(coronaKit)) {
			kitDAO.saveCoronaKit(coronaKitId, coronaKit);
		}
		return coronaKit;
	}

	@Override
	public CoronaKit getCoronaKit(int productId, KitDao kitDAO) throws ProductException {
		return kitDAO.getById(productId);
	}

}
