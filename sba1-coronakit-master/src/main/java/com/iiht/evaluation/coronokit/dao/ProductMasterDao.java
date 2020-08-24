package com.iiht.evaluation.coronokit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iiht.evaluation.coronokit.exception.ProductException;
import com.iiht.evaluation.coronokit.model.ProductMaster;



public class ProductMasterDao {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;
	
	public static final String INS_PROD_QRY = "INSERT INTO ProductMaster(productid,productname,productcost,productdesc) VALUES(?,?,?,?)";
	public static final String UPD_PROD_QRY = "UPDATE ProductMaster set productname=?,productcost=?,productdesc=? WHERE productid=?";
	public static final String DEL_PROD_QRY = "DELETE FROM ProductMaster WHERE productid=?";
	public static final String GET_PROD_BY_ID_QRY = "SELECT productid,productname,productcost,productdesc FROM ProductMaster WHERE productid=?";
	public static final String GET_ALL_PROD_QRY = "SELECT productid,productname,productcost,productdesc FROM ProductMaster";
	public static final String GET_LAST_ID_QRY="SELECT MAX(productid) FROM ProductMaster";

	public ProductMasterDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}

	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}
	
	public int getMaxProductId() throws ProductException {
		int id=0;
		try {
			connect();
			PreparedStatement pst = jdbcConnection.prepareStatement(GET_LAST_ID_QRY);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				id=rs.getInt(1);
			}
		} catch (SQLException exp) {
			throw new ProductException("Getting last Product id failed!");
		}
		return id;
	}

	public ProductMaster add(ProductMaster product) throws ProductException {

		if (product != null) {
			try {
				connect();
				PreparedStatement pst = jdbcConnection.prepareStatement(INS_PROD_QRY);
				pst.setInt(1, product.getId());
				pst.setString(2, product.getProductName());
				pst.setString(3, product.getCost());
				pst.setString(4, product.getProductDescription());

				pst.executeUpdate();
			} catch (SQLException exp) {
				throw new ProductException("Saving Product failed!");
			}
		}

		return product;
	}


	public ProductMaster save(ProductMaster product) throws ProductException {
		if (product != null) {
			try{
				connect();
				PreparedStatement pst = jdbcConnection.prepareStatement(UPD_PROD_QRY);
				pst.setString(1, product.getProductName());
				pst.setString(2, product.getCost());
				pst.setString(3, product.getProductDescription());
				pst.setInt(4, product.getId());

				pst.executeUpdate();
			} catch (SQLException exp) {
				throw new ProductException("Updating Product failed!");
			}
		}

		return product;
	}


	public boolean deleteById(int id) throws ProductException {
		boolean isDeleted = false;
		try {
			connect();
			PreparedStatement pst = jdbcConnection.prepareStatement(DEL_PROD_QRY);
			pst.setInt(1, id);

			int rowsCount = pst.executeUpdate();

			isDeleted = rowsCount > 0;

		} catch (SQLException exp) {
			throw new ProductException("Deleting Product failed!");
		}
		return isDeleted;
	}


	public ProductMaster getById(int id) throws ProductException {
		ProductMaster product = null;

		try{
			connect();
			PreparedStatement pst = jdbcConnection.prepareStatement(GET_PROD_BY_ID_QRY);
			pst.setInt(1, id);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				product = new ProductMaster();
				product.setId(rs.getInt(1));
				product.setProductName(rs.getString(2));
				product.setCost(rs.getString(3));
				product.setProductDescription(rs.getString(4));
			}

		} catch (SQLException exp) {
			throw new ProductException("Feteching Product failed!");
		}

		return product;
	}


	public List<ProductMaster> getAll() throws ProductException {
		List<ProductMaster> products = new ArrayList<>();
		
		try{
			
			connect();
			PreparedStatement pst = jdbcConnection.prepareStatement(GET_ALL_PROD_QRY);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				ProductMaster product = new ProductMaster();
				product.setId(rs.getInt(1));
				product.setProductName(rs.getString(2));
				product.setCost(rs.getString(3));
				product.setProductDescription(rs.getString(4));
				
				products.add(product);
			}
			
			if(products.isEmpty()) {
				products=null;
			}

		} catch (SQLException exp) {
			throw new ProductException("Feteching Products failed!");
		}
		
		return products;
	}
}