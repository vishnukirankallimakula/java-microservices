package com.accenture.lkm.dao;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.accenture.lkm.bean.ProductBean;
import com.accenture.lkm.entity.ProductEntity;

@Repository
public class ProductDAOWrapper {

	@Autowired
	private ProductDAO productDAO;

	public Integer updateProductStock(ProductBean bean) throws Exception {	
		
			return productDAO.updateProductStock(bean.getStock(), bean.getProductId());
			
	}

	public ProductBean findProductDetailsById(Integer productId) {

		return productDAO
				.findById(productId)
				.map(ProductDAOWrapper::convertProductEntityToBean)
				.orElse(new ProductBean(0, "Invalid", 0, 0));

	}

	public static ProductBean convertProductEntityToBean(ProductEntity productEntity) {
		ProductBean productBean = new ProductBean();
		BeanUtils.copyProperties(productEntity, productBean);
		return productBean;
	}

}
