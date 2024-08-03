package com.accenture.lkm.bean;

public class CustomerBean {

	private int customerId;
	private String customerEmail;
	private String customerType;

	public CustomerBean() {
		super();
	}

	
	public CustomerBean(int customerId, String customerEmail, String customerType) {
		super();
		this.customerId = customerId;
		this.customerEmail = customerEmail;
		this.customerType = customerType;
	}


	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	
}
