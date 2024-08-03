package com.accenture.lkm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.lkm.bean.CustomerBean;
import com.accenture.lkm.dao.CustomerDAOWrapper;

@Service
public class CustomerServiceImpl  implements CustomerService{
	
	@Autowired
	private CustomerDAOWrapper customerDAOWrapper;

	@Override
	public CustomerBean updateCustomerType(CustomerBean customerBean) throws Exception {
		
		System.out.println("hiiiiiiiiii");
		//Need to ask ::   what to return from customerserviceimpl
		//Case 1: if record found , message is framed
		//Case 2: if record not found , how to check, like in a controller we need to display message accordingly
		//so shall we return entire bean to controller and then check
		//if i write a business logic here again need to write in controller for conditional return
		//therefore removing the logic from here and putting in controller
		
		return customerDAOWrapper.updateCustomerType(customerBean);		
		
	}

	@Override
	public List<CustomerBean> getCustomerDetailsByType(String customerType) throws Exception {
		 return customerDAOWrapper.getCustomerDetailsByType(customerType);
	}

}
