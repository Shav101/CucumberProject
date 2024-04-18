package api_tests;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utilities.DataReader;

public class ApiLogout {
	String baseUrl = "http://crater.primetech-apps.com/api";
	Response response;
	String token;
	
	@Test (priority = 1)
	public void login() {
		String loginEndpoint = "/v1/auth/login";
		
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("username", DataReader.getProperty("apiusername"));
		requestBody.put("password", DataReader.getProperty("apipassword"));
		requestBody.put("device_name", DataReader.getProperty("device_name"));
		
		
		response = RestAssured.given()
		.accept("application/json")
		.contentType(ContentType.JSON)
		.body(requestBody)
		.when()
		.post(baseUrl + loginEndpoint)
		.thenReturn();
		
		response.prettyPrint();
		
		response.then().statusCode(200);
		token = response.jsonPath().getString("token");
		
	}
	
	
	@Test (dependsOnMethods = "login", priority = 2)
	public void logout() {
		
		String logoutEndpoint = "/v1/auth/logout";
		
		response = RestAssured.given()
		.accept("application/json")
		.contentType(ContentType.JSON)
		.auth().oauth2("Bearer "+token+"")
		.post(baseUrl + logoutEndpoint)
		.thenReturn();
		
		response.prettyPrint();
		
		response
		.then().statusCode(200)
		.body("success", Matchers.equalTo(true));
		
		
	}
	
	
	@Test (priority = 3)
	public void logoutWithIncorrectAuthorization() {
		
		String logoutEndpoint = "/v1/auth/logout";
		
		response = RestAssured.given()
		.accept("application/json")
		.contentType(ContentType.JSON)
		.auth().oauth2("Bearer "+token+"")
		.post(baseUrl + logoutEndpoint)
		.thenReturn();
		
		response.prettyPrint();
		
		response
		.then().statusCode(401)
		.body("message", Matchers.equalTo("Unauthenticated."));
		
		
		
	}
	
	@Test (priority = 4)
	public void logoutWithIncorrectEndpoint() {
		
		String logoutEndpoint = "/v1/auth/logout/login";
		
		response = RestAssured.given()
		.accept("application/json")
		.contentType(ContentType.JSON)
		.auth().oauth2("Bearer "+token+"")
		.post(baseUrl + logoutEndpoint)
		.thenReturn();
		
		response.prettyPrint();
		
		response
		.then().statusCode(404);
		
	}	
}
