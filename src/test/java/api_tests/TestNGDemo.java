package api_tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.sauceDemoPage;
import utilities.Driver;

public class TestNGDemo {
	
	sauceDemoPage page;
	
	@BeforeTest // FIRST
	public void beforeTest() {
		System.out.println("Before Test Method.");
	}
	
	
	@AfterTest
	public void afterTest() {
		System.out.println("Before Test Method.");
	}
	
	
	@BeforeMethod // SECOND
	public void beforeMethod() {
		System.out.println("Before Method.");
	}
	
	@AfterMethod
	public void afterMethod() {
		System.out.println("After Method.");
	}
	
	
	
	@Test (priority =0, description = "UI Test Demo in TestNG", groups={"smoketest", "regression"})
	public void uiTestDemo() {
		
		page = new sauceDemoPage();
		// go to https://saucedemo.com
		Driver.getDriver().get("https://saucedemo.com");
		//login with valid username = standard_user password = secret_sauce
		page.username.sendKeys("standard_user");
		page.password.sendKeys("secret_sauce");
		page.loginBtn.click();
		//verify that you are logged in successfully
		Assert.assertEquals(page.inventoryItems.size(), 6);
							// actual				//expected
		
	}
	
	@Test (priority = 10, description = "Hard Assert Demo", groups="smoketest")
	public void assertionDemo() {
		
		// HARD assert. If the condition fails, it stops the execution of the code.
		Assert.assertEquals(true, true);
		System.out.println("Hard assert. If the condition fails, it stops the execution of the code.");
		
	}
	
	@Test (priority = 4, description = "Soft Assert Demo", dependsOnMethods="assertionDemo") // depends on method will override the priority
	public void softAssertDemo() {
		// Soft assert. If the condition fails.
		// It DOES NOT  stops the execution of the code, but it marks the test as failed.
		SoftAssert softassert = new SoftAssert();
		
		softassert.assertEquals(true, true);
		System.out.println("Soft assert. If the condition fails.It DOES NOT  stops the execution of the code, but it marks the test as failed.");
		
		// In order for SOFT assert to mark the test failed with exception/error
		// we need to have .assertAll();
		softassert.assertAll();
		
	}

}
