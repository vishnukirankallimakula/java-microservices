package com.accenture.lkm.web.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {
			
		static public <T> T covertFromJsonToObject(String json, Class<T> var) throws IOException{
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, var);//Convert Json into object of Specific Type
		}

		public static String covertFromObjectToJson(Object obj) throws JsonProcessingException{
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
		}


}
