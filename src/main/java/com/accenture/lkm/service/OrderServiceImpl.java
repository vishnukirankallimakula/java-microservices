package com.accenture.lkm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.lkm.bean.CustomerBean;
import com.accenture.lkm.bean.OrderBean;
import com.accenture.lkm.dao.OrderDAOWrapper;
import com.accenture.lkm.dao.ProductDAOWrapper;

@Service
public class OrderServiceImpl implements OrderService {
	
	private OrderDAOWrapper orderDAOWrapper;	
	private ProductDAOWrapper productDAOWrapper;
	
	
	@Autowired
	public OrderServiceImpl(OrderDAOWrapper orderDAOWrapper, ProductDAOWrapper productDAOWrapper) {
		super();
		this.orderDAOWrapper = orderDAOWrapper;
		this.productDAOWrapper = productDAOWrapper;
	}

	@Override
	public List<OrderBean> getOrderDetailsByCustomerId(int customerId) throws Exception {
		return orderDAOWrapper.getOrderDetailsByCustomerId(customerId);
	}

	@Override
	public List<OrderBean> orderDetailsWithinBillingRangeAndCustomerType(String customerType,double minimum,double maximum) throws Exception {
		
				
		List<OrderBean> orderBeans=orderDAOWrapper.orderDetailsWithinBillingRange(customerType, minimum, maximum);	
		List<CustomerBean> customerBeans=orderDAOWrapper.getCustomerDetailsByType(customerType);
	
		//Apply stream
		//Use foreach to set Productname 
		
		orderBeans  //TODO add comment
		.stream()
		.forEach( orderBean ->  orderBean.setProductName(  productDAOWrapper.findProductDetailsById(orderBean.getProductId()).getProductName()));
		
		
		//Apply stream
		// 	
		for(CustomerBean customerBean: customerBeans)  //TODO add comments
		{
			orderBeans
			.stream()
			.filter( orderBean -> orderBean.getCustomerId()==customerBean.getCustomerId())
			.forEach(orderBean -> orderBean.setCustomerEmail(customerBean.getCustomerEmail()));	
		}		
		return orderBeans;
	}

}
