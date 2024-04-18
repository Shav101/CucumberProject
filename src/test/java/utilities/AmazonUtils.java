package utilities;

import java.util.Random;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AmazonUtils {
	
	Actions actions;
	Select letsSelect;
	WebDriverWait wait;
	
	
	public void waitUntilElementVisible(WebElement element) {
		
		wait = new WebDriverWait(Driver.getDriver(), 10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	// sendkeys via actions class to the field that is not interactable
	public void actionsSendkeys(WebElement element, String text) {
		
		actions = new Actions(Driver.getDriver());
		actions.sendKeys(element, text).build().perform();
		
		
	}
	
	
	// select by visible text
	public void selectByVisibleText(WebElement selectElement, String tobeSelectedOptionText) {
		letsSelect = new Select(selectElement);
		letsSelect.selectByVisibleText(tobeSelectedOptionText);
		
	}
	
	
	//return the selected option from the dropdown
	public String getSelectedOption(WebElement selectElement) {
		letsSelect = new Select(selectElement);
		String option = letsSelect.getFirstSelectedOption().getText();
		return option;
	}
	
	
	
	public void actionsSendKeysAmazon(WebElement element, String text) {
		actions = new Actions(Driver.getDriver());
		actions.sendKeys(element, text).build().perform();
		
	}
	
	public int randomNumber() {
		Random rand = new Random();
		int randomNum = rand.nextInt((999 - 100) + 1) + 100;
		return randomNum;
		
	}
	
	

}
