package com.accenture.lkm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.lkm.bean.ProductBean;
import com.accenture.lkm.dao.ProductDAOWrapper;

@Service
public class ProductServiceImpl implements ProductService{
	
	
	@Autowired
	private ProductDAOWrapper productDAOWrapper;
	
	
	
	public Integer updateProductStock(ProductBean bean) throws Exception
	{
		return productDAOWrapper.updateProductStock(bean);
	}

}
