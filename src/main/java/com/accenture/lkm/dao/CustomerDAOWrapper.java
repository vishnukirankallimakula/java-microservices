package com.accenture.lkm.dao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.accenture.lkm.bean.CustomerBean;
import com.accenture.lkm.entity.CustomerEntity;

@Repository
public class CustomerDAOWrapper {

	@Autowired
	private CustomerDAO customerDAO;


	public CustomerBean updateCustomerType(CustomerBean customerBean) throws Exception {

		Optional<CustomerEntity> customerEntity = customerDAO.findById(customerBean.getCustomerId());
		if(customerEntity.isPresent())
		{
		CustomerEntity customerEntity2 = customerEntity.get();		
		customerEntity2.setCustomerType(customerBean.getCustomerType());
		customerDAO.save(customerEntity2); // should i catch here , object of customerEntity returned by save method.		
		return convertCustomerEntityToBean(customerEntity2);
		}
		else		
		{			
			return convertCustomerEntityToBean(customerEntity.orElse(new CustomerEntity(0,"Invalid","Invalid")));
		}
	}

	public List<CustomerBean> getCustomerDetailsByType(String customerType) throws Exception {

		List<CustomerEntity> allCustomerEntities = customerDAO.getCustomerDetailsByType(customerType);
		return allCustomerEntities
				.stream()
				.map(CustomerDAOWrapper::convertCustomerEntityToBean)
				.collect(Collectors.toList());

	}

	public static CustomerBean convertCustomerEntityToBean(CustomerEntity entity) {
		CustomerBean bean = new CustomerBean();
		BeanUtils.copyProperties(entity, bean);
		return bean;
	}

	public static CustomerEntity convertCustomerBeanToEntity(CustomerBean bean) {
		CustomerEntity entity = new CustomerEntity();
		BeanUtils.copyProperties(bean, entity);
		return entity;
	}

}
