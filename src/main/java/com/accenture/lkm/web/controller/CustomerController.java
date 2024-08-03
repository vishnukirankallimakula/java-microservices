package com.accenture.lkm.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.lkm.bean.CustomerBean;
import com.accenture.lkm.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	// http://localhost:8797/customer/controller/updateCustomer
	@RequestMapping(value = "/customer/controller/updateCustomer",
			method = RequestMethod.PATCH, 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> updateCustomerType(@RequestBody CustomerBean customerBean) throws Exception {
		CustomerBean customerBean2 = customerService.updateCustomerType(customerBean);
		
		if (customerBean2.getCustomerId()!= 0) {			
			return new ResponseEntity<String>("Hello "+ customerBean2.getCustomerEmail() + " customer type is upadted as  "+ customerBean2.getCustomerType()+ " successfully ." , HttpStatus.OK);			
		} else {
			return new ResponseEntity<String>("Please enter valid details." ,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	// http://localhost:8797/customer/controller/getCustomersByType/Gold
	@RequestMapping(value = "customer/controller/getCustomersByType/{customerType}",
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustomerBean>> getCustomerDetailsByType(
			@PathVariable("customerType") String customerType) throws Exception {

		List<CustomerBean> customerBeans = customerService.getCustomerDetailsByType(customerType);

		if ( ! customerBeans.isEmpty()) {
			return new ResponseEntity<List<CustomerBean>>(customerBeans, HttpStatus.OK);
		} else {			
			return new ResponseEntity<List<CustomerBean>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	

}
