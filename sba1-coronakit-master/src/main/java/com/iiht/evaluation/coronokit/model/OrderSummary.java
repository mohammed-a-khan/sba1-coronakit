package com.iiht.evaluation.coronokit.model;

import java.util.List;

public class OrderSummary {
	private CoronaKit coronaKit;
	private List<KitItemDetails> kitItemDetails;
	
	public OrderSummary() {
		// TODO Auto-generated constructor stub
	}
	
	public OrderSummary(CoronaKit coronaKit, List<KitItemDetails> kitItemDetails) {
		
		this.coronaKit = coronaKit;
		this.kitItemDetails = kitItemDetails;
	}
	public CoronaKit getCoronaKit() {
		return coronaKit;
	}
	public void setCoronaKit(CoronaKit coronaKit) {
		this.coronaKit = coronaKit;
	}
	public List<KitItemDetails> getKitDetails() {
		return kitItemDetails;
	}
	public void setKitDetails(List<KitItemDetails> kitItemDetails) {
		this.kitItemDetails = kitItemDetails;
	}
	
	
}
