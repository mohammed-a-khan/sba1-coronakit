package com.iiht.evaluation.coronokit.service;

import java.util.List;

import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.exception.ProductException;
import com.iiht.evaluation.coronokit.model.ProductMaster;


public interface ProductService {

	ProductMaster validateAndAdd(ProductMaster product, ProductMasterDao productMasterDao) throws ProductException;
	ProductMaster validateAndSave(ProductMaster product, ProductMasterDao productMasterDao) throws ProductException;
	boolean deleteProduct(int productId, ProductMasterDao productMasterDao) throws ProductException;
	ProductMaster getProduct(int productId, ProductMasterDao productMasterDao) throws ProductException;
	List<ProductMaster> getAllProducts(ProductMasterDao productMasterDao) throws ProductException;
}
