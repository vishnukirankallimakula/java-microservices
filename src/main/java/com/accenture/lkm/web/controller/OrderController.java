package com.accenture.lkm.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.lkm.bean.OrderBean;
import com.accenture.lkm.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	
	
	@RequestMapping(value="/")
	public String welcomePage()
	{
		return "Welcome to customer product management application";
	}
	
	
	
	

	// http://localhost:8797/order/controller/getOrderDetails/1001
	@RequestMapping(value = "/order/controller/getOrderDetails/{custId}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderBean>> getOrderDetailsByCustomerId(@PathVariable("custId") int customerId)
			throws Exception {
		
		
		 
		 List<OrderBean> allOrders = orderService.getOrderDetailsByCustomerId(customerId);
		if ( allOrders != null) {
			return new ResponseEntity<List<OrderBean>>(allOrders, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<OrderBean>>(HttpStatus.NO_CONTENT);
		}
	}

	// http://localhost:8797/order/controller/ordersWithinRange/Gold/3000--14000
	@RequestMapping(value = "/order/controller/ordersWithinRange/{customerType}/{min}--{max}")
	public ResponseEntity<List<OrderBean>> orderDetailsWithinRagne(@PathVariable("customerType") String customerType,
			@PathVariable("min") double minimum, @PathVariable("max") double maximum) throws Exception {
		
		 List<OrderBean> allOrders = orderService.orderDetailsWithinBillingRangeAndCustomerType(customerType, minimum, maximum);

		if ( allOrders != null ) {
			return new ResponseEntity<List<OrderBean>>(allOrders, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<OrderBean>>(HttpStatus.NO_CONTENT);
		}
	}
	
	

}
