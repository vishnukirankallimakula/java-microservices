package com.accenture.lkm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.accenture.lkm.entity.OrderEntity;


public interface OrderDAO extends JpaRepository<OrderEntity, Integer> {

	public List<OrderEntity> findByCustomerId(int customerId) throws Exception;

	@Query(name = "OrderDAO.orderDetailsWithingRange")
	public List<OrderEntity> getOrderDetails(@Param("customerIds") List<Integer> customerIds,
			@Param("min") double minimum, @Param("max") double maximum);

}
