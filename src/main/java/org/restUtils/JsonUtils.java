package org.restUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.base.BaseClass;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils extends BaseClass{
	
	public static final ObjectMapper objectMapper = new ObjectMapper();
	
	public static Map getJsonAsMap(String path) {
		File file = new File(path);
		try {
			return objectMapper.readValue(file, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
