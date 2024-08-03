package com.accenture.lkm.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.accenture.lkm.bean.CustomerBean;
import com.accenture.lkm.bean.OrderBean;
import com.accenture.lkm.entity.CustomerEntity;
import com.accenture.lkm.entity.OrderEntity;
import com.accenture.lkm.entity.ProductEntity;

@Repository
public class OrderDAOWrapper {

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private ProductDAO productDAO;

	public List<OrderBean> getOrderDetailsByCustomerId(int customerId) throws Exception {
		List<OrderBean> orderBeans=null ;

		List<OrderEntity> orderEntities = orderDAO.findByCustomerId(customerId);		

		if (!orderEntities.isEmpty()) {
			orderBeans = new ArrayList<>();
			for (OrderEntity entity : orderEntities) {
				OrderBean orderBean = convertOrderEntityToOrderBean(entity);
				String customerEmail = customerDAO.findById(orderBean.getCustomerId())
						.orElse(new CustomerEntity(0, "Invalid", "Invalid")).getCustomerEmail();
				String productName = productDAO.findById(orderBean.getProductId())
						.orElse(new ProductEntity(0, "Invalid", 0, 0)).getProductName();
				
				orderBean.setCustomerEmail(customerEmail);
				orderBean.setProductName(productName);

				orderBeans.add(orderBean);
			}
		}
		
		return orderBeans;
	}

	public List<CustomerBean> getCustomerDetailsByType(String customerType) throws Exception {
		List<CustomerEntity> customerEntities = customerDAO.getCustomerDetailsByType(customerType);

		List<CustomerBean> customerBeans = null;
		if (customerEntities != null) {
			customerBeans = new ArrayList<>();
			for (CustomerEntity customerEntity : customerEntities) {
				CustomerBean customerBean = new CustomerBean();
				BeanUtils.copyProperties(customerEntity, customerBean);
				customerBeans.add(customerBean);
			}
		}
		return customerBeans;

	}

	public List<OrderBean> orderDetailsWithinBillingRange(String customerType, double minimum, double maximum)
			throws Exception {

		List<CustomerEntity> all = customerDAO.getCustomerDetailsByType(customerType);

		List<Integer> allCustomerId = new ArrayList<>();
		for (CustomerEntity entity : all) {
			allCustomerId.add(entity.getCustomerId());
		}

		List<OrderEntity> orderEntities = orderDAO.getOrderDetails(allCustomerId, minimum, maximum);

		List<OrderBean> orderBeans = null;
		if ( ! orderEntities.isEmpty()) {
			orderBeans = new ArrayList<>();
			for (OrderEntity entity : orderEntities) {
				OrderBean orderBean = convertOrderEntityToOrderBean(entity);
				orderBeans.add(orderBean);
			}
		}
		return orderBeans;
	}

	public OrderBean convertOrderEntityToOrderBean(OrderEntity entity) {
		OrderBean bean = new OrderBean();
		BeanUtils.copyProperties(entity, bean);
		return bean;
	}

	public OrderEntity convertOrderBeanToEntity(OrderBean bean) {
		OrderEntity entity = new OrderEntity();
		BeanUtils.copyProperties(bean, entity);
		return entity;
	}

}
