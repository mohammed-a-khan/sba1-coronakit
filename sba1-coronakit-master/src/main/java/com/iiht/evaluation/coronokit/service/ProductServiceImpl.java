package com.iiht.evaluation.coronokit.service;

import java.util.ArrayList;
import java.util.List;

import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.exception.ProductException;
import com.iiht.evaluation.coronokit.model.ProductMaster;


public class ProductServiceImpl implements ProductService {


	private boolean isValidProductName(String productName) {
		return productName!=null && (productName.length()>=3 || productName.length()<=100);
	}
	
	private boolean isValidProductCost(String productCost) {
		return productCost!=null && (productCost.length()>=1 || productCost.length()<=8) && (!productCost.trim().equals("0"));
	}
	
	private boolean isValidProductDescription(String productDesc) {
		return productDesc!=null && (productDesc.length()>=1) && (!productDesc.trim().equals(""));
	}
	
	public boolean isValidProduct(ProductMaster product) throws ProductException {
		List<String> errMsgs = new ArrayList<>();
		boolean isValid=true;
		
		if(product!=null) {
			
			if(!isValidProductName(product.getProductName())) {
				isValid=false;
				errMsgs.add("Product name can not left blank and must be of 3 to 20 in length");
			}
			if(!isValidProductCost(product.getCost())) {
				isValid=false;
				errMsgs.add("Product cost can not be left blank and must be a greater than 0");
			}
			if(!isValidProductDescription(product.getProductDescription())) {
				isValid=false;
				errMsgs.add("Product description can not be left blank");
			}
			
			if(!errMsgs.isEmpty()) {
				throw new ProductException("Invalid Product: "+errMsgs);
			}
		}else {
			isValid=false;
			throw new ProductException("Product details are not supplied");
		}
		
		return isValid;
	}
	
	@Override
	public ProductMaster validateAndAdd(ProductMaster product, ProductMasterDao productMasterDao) throws ProductException {
		if(isValidProduct(product)) {
			productMasterDao.add(product);
		}
		return product;
	}

	@Override
	public ProductMaster validateAndSave(ProductMaster product, ProductMasterDao productMasterDao) throws ProductException {
		if(isValidProduct(product)) {
			productMasterDao.save(product);
		}
		return product;
	}

	@Override
	public boolean deleteProduct(int productId, ProductMasterDao productMasterDao) throws ProductException {
		return productMasterDao.deleteById(productId);
	}

	@Override
	public ProductMaster getProduct(int contactId, ProductMasterDao productMasterDao) throws ProductException {
		return productMasterDao.getById(contactId);
	}

	@Override
	public List<ProductMaster> getAllProducts(ProductMasterDao productMasterDao) throws ProductException {
		return productMasterDao.getAll();
	}

}
