package com.accenture.lkm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class CustomerEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int customerId;
	private String customerEmail; 
	private String customerType;
	
	public CustomerEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public CustomerEntity(int customerId, String customerEmail, String customerType) {
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
