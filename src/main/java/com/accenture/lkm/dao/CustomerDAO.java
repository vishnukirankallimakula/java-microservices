package com.accenture.lkm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.lkm.entity.CustomerEntity;


public interface CustomerDAO extends JpaRepository<CustomerEntity, Integer> {

	@Query(name = "CustomerDAO.getCustomerDetailsByType")
	public List<CustomerEntity> getCustomerDetailsByType(@Param("custType") String customerType);

	public CustomerEntity findByCustomerEmail(String customerEmail);


}
