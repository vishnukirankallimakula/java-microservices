package com.accenture.lkm.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.lkm.Application;
import com.accenture.lkm.bean.ProductBean;
import com.accenture.lkm.service.ProductService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=Application.class)
@Transactional
class ProductServiceTest {

	@Autowired
	private ProductService productService;
	
	@Test
	public void testUpdateProductStock() throws Exception
	{
		ProductBean productBean= new ProductBean(1,"Bag",999,450);
		 int result=  productService.updateProductStock(productBean);		 
		 Assertions.assertEquals(result, 1);
		
	}
	
	@Test
	public void testUpdateProductStockInValid() throws Exception
	{
		ProductBean productBean= new ProductBean(111,"Bag",999,450);
		 int result=  productService.updateProductStock(productBean);		
		 Assertions.assertEquals(result, 0);
	}
	
	
	
	
	
	
	
	
	

}
