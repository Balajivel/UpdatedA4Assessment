package org.restUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Map;

import org.base.BaseClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.restassured.RestAssured;
import io.restassured.response.Response;



public class RestApiUtils extends BaseClass{
	
	public static String url = propertiesFileDataAccess("baseURI");
	private static final String BASE_URL = url;
	
	public static Response sendGetRequest(String endpoint) {
		return RestAssured.get(BASE_URL + endpoint);

	}
	
	public static Response sendPostRequest(String endpoint, Map<String, String> requestBody) {
		return RestAssured.given().contentType("application/json").body(requestBody).post(BASE_URL +endpoint);

	}
	
	public static Response listGetUserValue() {
		String getListUsers = propertiesFileDataAccess("get_list_users");
		Response response = RestApiUtils.sendGetRequest(getListUsers);
		return response;
	}
	
	public static Response singleGetUserValue() {
		String singleGetUser = propertiesFileDataAccess("get_single_user");
		Response response = RestApiUtils.sendGetRequest(singleGetUser);
		return response;
	}
	
	public static Response postCreateValue() {
		
		String requestBody = propertiesFileDataAccess("json_file_name");
		Map<String, String> jsonAsMap = JsonUtils
				.getJsonAsMap(System.getProperty("user.dir") + "//" + requestBody + ".json");
		String postCreateUser = propertiesFileDataAccess("post_create_user");
		Response response = RestApiUtils.sendPostRequest(postCreateUser, jsonAsMap);
		return response;

	}
	
	public static void getRecordValidation(Response response) {
		int getStatusCode = Integer.parseInt(propertiesFileDataAccess("get_status_code"));
		assertEquals(response.getStatusCode(), getStatusCode);
		assertTrue(response.getBody().asString().contains("data"));
		assertTrue(response.getBody().asString().contains("id"));
		assertTrue(response.getBody().asString().contains("email"));

	}
	
	public static void postRecordValidation(Response response) {
		int postStatusCode = Integer.parseInt(propertiesFileDataAccess("post_status_code"));
		assertEquals(response.getStatusCode(), postStatusCode);
		assertTrue(response.getBody().asString().contains("id"));
		assertTrue(response.getBody().asString().contains("createdAt"));

	}
	
	public static void singleUserValidation() {
		getRecordValidation(singleGetUserValue());

	}
	
	public static void listUsersValidation() {
		getRecordValidation(listGetUserValue());

	}
	
	public static void postUserValidation() {
		postRecordValidation(postCreateValue());

	}
	

}
