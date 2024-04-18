package step_definitions;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utilities.BrowserUtils;
import utilities.DataReader;
import utilities.Driver;

public class UserManagementSteps {

	LoginPage loginpage = new LoginPage();
	BrowserUtils utils = new BrowserUtils();
	
	String emailAddress;
	String passwordInput;
	
	
	// valid log scenario steps
	
	@Given("As a user, I am on the login page")
	public void as_a_user_i_am_on_the_login_page() {
	   Driver.getDriver().get(DataReader.getProperty("appUrl"));
	}
	@When("I enter valid username and valid password")
	public void i_enter_valid_username_and_valid_password() {
		utils.actionsSendKeys(loginpage.emailField, DataReader.getProperty("username"));
		utils.actionsSendKeys(loginpage.passwordField, DataReader.getProperty("password"));
//		loginpage.emailField.sendKeys(DataReader.getProperty("username"));
//		loginpage.passwordField.sendKeys(DataReader.getProperty("password"));
	}
	@When("I click on login button")
	public void i_click_on_login_button() {
		loginpage.loginBtn.click();
	}
	@Then("I should be on user profile page")
	public void i_should_be_on_user_profile_page() {
		Assert.assertTrue(loginpage.accountSettingsHeader.isDisplayed());
	}

	
	// invalid login scenario steps
	
	@When("I enter invalid username and valid password")
	public void i_enter_invalid_username_and_valid_password() {
	   utils.actionsSendKeys(loginpage.emailField, "invalid@primetechschool.com");
	   utils.actionsSendKeys(loginpage.passwordField, DataReader.getProperty("password"));
	}
	@Then("I should see an error message")
	public void i_should_see_an_error_message() throws InterruptedException {
		Thread.sleep(1000);
		utils.waitUntilElementVisible(loginpage.invalidLoginErrorMessage);
		Assert.assertTrue(loginpage.invalidLoginErrorMessage.isDisplayed());
	}
	@Then("I should not be logged in")
	public void i_should_not_be_logged_in() {
		Assert.assertTrue(loginpage.emailField.isDisplayed());
	}
	
	// invalid password login steps
	
	@When("I enter valid username and invalid password")
	public void i_enter_valid_username_and_invalid_password() {
		utils.actionsSendKeys(loginpage.emailField, DataReader.getProperty("username"));
		utils.actionsSendKeys(loginpage.passwordField, "helloWorld123");
	}
	
	
	// scenario outline steps

	@When("I enter email {string} and password {string}")
	public void i_enter_email_and_password(String email, String password) {
		emailAddress = email;
		passwordInput = password;
	    utils.actionsSendKeys(loginpage.emailField, email);
	    utils.actionsSendKeys(loginpage.passwordField, password);
	}

	
	
	

}
