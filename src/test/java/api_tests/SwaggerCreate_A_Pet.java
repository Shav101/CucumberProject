package api_tests;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SwaggerCreate_A_Pet {

	String baseURL = "https://petstore.swagger.io/v2";
	String petId = "id: 101";
	String petName = "\"name\": PewPewDoggie";
	Response response;
	
	
	@Test
	public void createApet_Post() {

		// Go to https://petstore.swagger.io/
		// 1.Then come to eclipse, create a testNg test called createApet() and
		// implement a pet creation logic.
		// Hint, when you use POST request, you need to provide body() to request header.
		// create a string variable for the body and provide that to body() method.
		
		
		String postEndPoint = "/pet";
		given().contentType("application/json")
		.accept(ContentType.JSON)
		.body(petId)
		.body(petName)
		.then().statusCode(200)
		.and().contentType("application/json");
		
		System.out.println(petId);
		System.out.println(petName);
		
		response.prettyPrint();
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");
		
		
		// Validate that statuscode is 200,
		// Content type is "applicaion/json"
	}
	
	@Test
	public void get_ApetById() {

		// 2. Then create another test called getApetById(),
		// and use the id that you provided when you created a pet in another test to
		// find the pet.
		String getEndPoint = "pet/101";
		response = given().contentType("application/json")
		.accept(ContentType.JSON)
		.body(petId)
		.queryParam("id: 101", petId)
		.when().get(baseURI + getEndPoint)
		.thenReturn();

		response.prettyPrint();
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");
		// Validate that statuscode is 200,
		// Content type is "applicaion/json"

	}

}
