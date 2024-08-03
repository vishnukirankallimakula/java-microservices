package com.accenture.lkm.service;

import java.util.List;

import com.accenture.lkm.bean.OrderBean;

public interface OrderService {
	
	public List<OrderBean> getOrderDetailsByCustomerId(int customerId)throws Exception;
	public List<OrderBean> orderDetailsWithinBillingRangeAndCustomerType(String customerType,double minimum,double maximum) throws Exception;
}
