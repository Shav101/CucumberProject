package pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Driver;

public class sauceDemoPage {
	
	public sauceDemoPage() {
		
		PageFactory.initElements(Driver.getDriver(), this);
		
	}

	
	@FindBy (id = "user-name")
	public WebElement username;
	
	@FindBy (id = "password")
	public WebElement password;
	
	@FindBy (id = "login-button")
	public WebElement loginBtn;
	
	@FindBy (css = ".inventory_item")
	public List<WebElement> inventoryItems;
	
	
	
}
