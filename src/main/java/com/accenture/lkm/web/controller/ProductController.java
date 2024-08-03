package com.accenture.lkm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.lkm.bean.ProductBean;
import com.accenture.lkm.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	// http://localhost:8797/product/controller/updateProductStock
	@RequestMapping(value = "/product/controller/updateProductStock", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> updateProductStock(@RequestBody ProductBean productBean) throws Exception {
		Integer result = productService.updateProductStock(productBean);

		if (result >= 1) {
			return new ResponseEntity<String>("Stock updated successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No product found for given id", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
