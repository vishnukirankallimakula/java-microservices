package com.accenture.lkm.service;

import java.util.List;

import com.accenture.lkm.bean.CustomerBean;

public interface CustomerService {	

	public CustomerBean updateCustomerType(CustomerBean customerBean) throws Exception ;
	public List<CustomerBean> getCustomerDetailsByType(String customerType) throws Exception;

}
