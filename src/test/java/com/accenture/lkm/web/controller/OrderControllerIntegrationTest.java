package com.accenture.lkm.web.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.accenture.lkm.Application;
import com.accenture.lkm.bean.OrderBean;
import com.accenture.lkm.web.utils.JSONUtils;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@Transactional
@WebAppConfiguration
class OrderControllerIntegrationTest {

	@Autowired
	WebApplicationContext webApplicationContext;

	protected MockMvc mockMvc;

	@BeforeEach
	public void mySetup() {
		// making the mockMVC aware of the all the application components
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	// GetOrderdetailsbyorderId
	@SuppressWarnings("unchecked")
	@Test
	public void getOrderDetailsValid() throws Exception {

		String uri = "/order/controller/getOrderDetails/1001";
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);
		ResultActions rest = mockMvc.perform(request);
		MvcResult mvcREsult = rest.andReturn();

		// actual status and result
		String result = mvcREsult.getResponse().getContentAsString();
		int actualStatus = mvcREsult.getResponse().getStatus();

		List<OrderBean> orderBeans = JSONUtils.covertFromJsonToObject(result, List.class);
		
		Assertions.assertNotNull(orderBeans);
		Assertions.assertEquals(actualStatus, HttpStatus.OK.value());
	}

	@Test
	public void getOrderDetailsInValid() throws Exception {
		String uri = "/order/controller/getOrderDetails/1022";
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);
		ResultActions rest = mockMvc.perform(request);  //getting error here
		MvcResult mvcREsult = rest.andReturn();
				System.out.println("------->"+mvcREsult.getResponse().getStatus());
		//Assertions.assertEquals( mvcREsult.getResponse().getStatus(), HttpStatus.NO_CONTENT.value() );	
		Assertions.assertEquals(mvcREsult.getResponse().getStatus(), HttpStatus.NO_CONTENT.value());
		//tried to test with httpstatus code, but returning null from daowrapper		 
	}
	
	
	
	
	
	

}
