package com.iiht.evaluation.coronokit.model;

public class KitItemDetails {

	private int id;
	private int coronaKitId;
	private int productId;
	private String productName;
	private int quantity;
	private int amount;
	
	public KitItemDetails() {
		// TODO Auto-generated constructor stub
	}
	
	public KitItemDetails(int id, int coronaKitId, int productId, String productName, int quantity, int amount) {
		this.id = id;
		this.coronaKitId = coronaKitId;
		this.productId = productId;
		this.productName=productName;
		this.quantity = quantity;
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getCoronaKitId() {
		return coronaKitId;
	}
	public void setCoronaKitId(int coronaKitId) {
		this.coronaKitId = coronaKitId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
