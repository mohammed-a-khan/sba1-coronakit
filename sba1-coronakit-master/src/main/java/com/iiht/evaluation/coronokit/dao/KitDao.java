package com.iiht.evaluation.coronokit.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iiht.evaluation.coronokit.exception.ProductException;
import com.iiht.evaluation.coronokit.model.CoronaKit;
import com.iiht.evaluation.coronokit.model.KitDetail;
import com.iiht.evaluation.coronokit.model.KitItemDetails;



public class KitDao {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;
	

	public static final String INS_KIT_ITEM_QRY = "INSERT INTO Kit(kitid,coronaKitId,productId,quantity,amount) VALUES(?,?,?,?,?)";
	public static final String UPD_KIT_ITEM_QRY = "UPDATE Kit set quantity=?,amount=? WHERE kitid=?";
	public static final String DEL_KIT_ITEM_QRY = "DELETE FROM Kit WHERE kitid=?";
	public static final String GET_KIT_ALL_ITEM_QRY = "SELECT k.kitid,k.productId,p.productname,k.quantity,k.amount FROM ProductMaster p "
			+ "INNER JOIN Kit k on k.productId=p.productId "
			+ "INNER JOIN CoronaKit c on c.coronaKitId=k.coronaKitId "
			+ " WHERE c.coronaKitId=?";
	public static final String GET_LAST_KIT_ID_QRY="SELECT MAX(kitid) FROM Kit";
	public static final String GET_LAST_CORONA_KIT_ID_QRY="SELECT MAX(coronaKitId) FROM CoronaKit";
	public static final String INS_CORONA_KIT_ITEM_QRY = "INSERT INTO CoronaKit(coronaKitId,pname,pemail,contactNumber) VALUES(?,?,?,?)";
	public static final String UPD_CORONAKIT_ITEM_QRY = "UPDATE CoronaKit set totalamount=?,deliveryAddress=?,orderDate=?,orderFinalized=? WHERE coronaKitId=?";
	public static final String GET_CORONA_KIT_BY_ID_QRY = "SELECT coronaKitId,pname,pemail,contactNumber,"
			+ "totalamount,deliveryAddress,orderDate,orderFinalized FROM CoronaKit WHERE coronaKitId=?";

	public KitDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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

	public int getMaxKitId() throws ProductException {
		int id=0;
		try {
			connect();
			PreparedStatement pst = jdbcConnection.prepareStatement(GET_LAST_KIT_ID_QRY);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				id=rs.getInt(1);
			}
		} catch (SQLException exp) {
			throw new ProductException("Getting last Kit item id failed!");
		}
		return id;
	}
	
	public int getMaxCoronaKitId() throws ProductException {
		int id=0;
		try {
			connect();
			PreparedStatement pst = jdbcConnection.prepareStatement(GET_LAST_CORONA_KIT_ID_QRY);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				id=rs.getInt(1);
			}
		} catch (SQLException exp) {
			throw new ProductException("Getting last Corona Kit id failed!");
		}
		return id;
	}

	public KitDetail add(KitDetail kitDetail) throws ProductException {

		if (kitDetail != null) {
			try {
				connect();
				PreparedStatement pst = jdbcConnection.prepareStatement(INS_KIT_ITEM_QRY);
				pst.setInt(1, kitDetail.getId());
				pst.setInt(2, kitDetail.getCoronaKitId());
				pst.setInt(3, kitDetail.getProductId());
				pst.setInt(4, kitDetail.getQuantity());
				pst.setInt(5, kitDetail.getAmount());

				pst.executeUpdate();
			} catch (SQLException exp) {
				throw new ProductException("Saving Kit Item failed!");
			}
		}

		return kitDetail;
	}


	public KitDetail save(KitDetail kitDetail) throws ProductException {
		if (kitDetail != null) {
			try{
				connect();
				PreparedStatement pst = jdbcConnection.prepareStatement(UPD_KIT_ITEM_QRY);
				pst.setInt(1, kitDetail.getQuantity());
				pst.setInt(2, kitDetail.getAmount());

				pst.executeUpdate();
			} catch (SQLException exp) {
				throw new ProductException("Updating Kit Item Quantity failed!");
			}
		}

		return kitDetail;
	}


	public boolean deleteById(int id) throws ProductException {
		boolean isDeleted = false;
		try {
			connect();
			PreparedStatement pst = jdbcConnection.prepareStatement(DEL_KIT_ITEM_QRY);
			pst.setInt(1, id);

			int rowsCount = pst.executeUpdate();

			isDeleted = rowsCount > 0;

		} catch (SQLException exp) {
			throw new ProductException("Deleting Kit item failed!");
		}
		return isDeleted;
	}


	public CoronaKit getById(int id) throws ProductException {
		CoronaKit coronaKit = null;

		try{
			connect();
			PreparedStatement pst = jdbcConnection.prepareStatement(GET_CORONA_KIT_BY_ID_QRY);
			pst.setInt(1, id);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				coronaKit = new CoronaKit();
				coronaKit.setId(rs.getInt(1));
				coronaKit.setPersonName(rs.getString(2));
				coronaKit.setEmail(rs.getString(3));
				coronaKit.setContactNumber(rs.getString(4));
				coronaKit.setTotalAmount(rs.getInt(5));
				coronaKit.setDeliveryAddress(rs.getString(6));
				coronaKit.setOrderDate(rs.getString(7));
				coronaKit.setOrderFinalized(rs.getBoolean(8));
			}

		} catch (SQLException exp) {
			throw new ProductException("Feteching Product failed!");
		}

		return coronaKit;
	}


	public List<KitItemDetails> getAll(int coronaKitId) throws ProductException {
		List<KitItemDetails> kitItemDetails = new ArrayList<>();
		
		try{
			
			connect();
			PreparedStatement pst = jdbcConnection.prepareStatement(GET_KIT_ALL_ITEM_QRY);
			pst.setInt(1, coronaKitId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				KitItemDetails kitItem = new KitItemDetails();
				kitItem.setId(rs.getInt(1));
				kitItem.setProductId(rs.getInt(2));
				kitItem.setProductName(rs.getString(3));
				kitItem.setQuantity(rs.getInt(4));
				kitItem.setAmount(rs.getInt(5));
				
				kitItemDetails.add(kitItem);
			}
			
			if(kitItemDetails.isEmpty()) {
				kitItemDetails=null;
			}

		} catch (SQLException exp) {
			throw new ProductException("Feteching Products failed!");
		}
		
		return kitItemDetails;
	}
	
	public CoronaKit addCoronaKit(CoronaKit coronaKit) throws ProductException {

		if (coronaKit != null) {
			try {
				connect();
				PreparedStatement pst = jdbcConnection.prepareStatement(INS_CORONA_KIT_ITEM_QRY);
				pst.setInt(1, coronaKit.getId());
				pst.setString(2, coronaKit.getPersonName());
				pst.setString(3, coronaKit.getEmail());
				pst.setString(4, coronaKit.getContactNumber());

				pst.executeUpdate();
			} catch (SQLException exp) {
				throw new ProductException("Adding new Corona Kit failed!");
			}
		}

		return coronaKit;
	}


	public CoronaKit saveCoronaKit(int coronaKitId, CoronaKit coronaKit) throws ProductException {
		if (coronaKit != null) {
			try{
				connect();
				PreparedStatement pst = jdbcConnection.prepareStatement(UPD_CORONAKIT_ITEM_QRY);
				pst.setInt(1, coronaKit.getTotalAmount());
				pst.setString(2, coronaKit.getDeliveryAddress());
				pst.setString(3, coronaKit.getOrderDate());
				pst.setBoolean(4, coronaKit.isOrderFinalized());
				pst.setInt(5, coronaKit.getId());

				pst.executeUpdate();
			} catch (SQLException exp) {
				throw new ProductException("Updating Corona Kit details failed!");
			}
		}
		return coronaKit;
	}
}