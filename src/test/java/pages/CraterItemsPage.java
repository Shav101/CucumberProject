package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.BrowserUtils;
import utilities.Driver;

public class CraterItemsPage {

	public CraterItemsPage() {
		PageFactory.initElements(Driver.getDriver(), this);
	}

	@FindBy(xpath = "//h3[text()='Items']")
	public WebElement itemsPageHeaderText;

	@FindBy(xpath = "//button[text()=' Add Item']")
	public WebElement addItemButton;

	@FindBy(xpath = "//h3[text()='New Item']")
	public WebElement addItemPageHeaderText;

	@FindBy(xpath = "(//input[@type='text'])[2]")
	public WebElement addItemName;

	@FindBy(xpath = "//div[text()='Price ']//parent::label//following-sibling::div/input")
	public WebElement addItemPrice;

	@FindBy(xpath = "//div[text()='select unit']//preceding::input[1]")
	public WebElement addItemUnit;

	@FindBy(xpath = "//span[text()='pc']")
	public WebElement addItem_pc_unit;

	@FindBy(xpath = "//span[text()='%s']")
	public WebElement unitOptions;

	@FindBy(name = "description")
	public WebElement addItemDesciption;

	@FindBy(xpath = "//button[text()=' Save Item']")
	public WebElement saveItemButton;

	// a[text()='Soccer']

	@FindBy(xpath = "//h3[text()='Edit Item']")
	public WebElement editItemHeaderText;

	@FindBy(css = "button[type='submit']")
	public WebElement updateButton;

	@FindBy(xpath = "//button[text()='Filter ']")
	public WebElement filterButton;

	@FindBy(xpath = "//div[@name='name']")
	public WebElement filterNameBox;

	@FindBy(xpath = "//a[text()='%s']")
	public WebElement itemNameInTheItemsTable;

	@FindBy(xpath = "(//button[contains(@id, 'headlessui')])[3]")
	public WebElement filterItem3dot;

	@FindBy(xpath = "//a[text()=' Delete']")
	public WebElement filter3dotDeleteBtn;

	@FindBy(xpath = "//button[text()='Ok']")
	public WebElement itemDeleteOkayBtn;

	@FindBy(xpath = "//span[text()='No Results Found']")
	public WebElement filterNoResultFoundMessage;

	@FindBy(xpath = "//p[contains(text(), 'Success!')]")
	public WebElement itemCreateSuccessMessage;

	/*
	 * In ItemsPage class, create a method which accepts 4 parameters, itemName,
	 * itemPrice, itemUnit, and itemDescription and based on these parameters,
	 * implement the item creation logic in the method.
	 * 
	 * Acceptance criteria: You should be able to call the method and provide 4
	 * parameters, and the method should create an item in the items page.
	 * 
	 * Note: just implement the item creation part starting with itemName till the
	 * Add Item button.
	 */

	BrowserUtils utils = new BrowserUtils();

	public void createAnItem(String itemName, String itemPrice, String itemUnit, String itemDes) {
		addItemName.sendKeys(itemName);
		addItemPrice.sendKeys(itemPrice);
		addItemUnit.click();
		utils.waitUntilElementVisible(addItem_pc_unit);
//		unitOptions.findElement(
//				By.xpath(String.format(unitOptions.getAttribute("xpath"), itemUnit))).click();
		Driver.getDriver().findElement(By.xpath("//span[text()='" + itemUnit + "']")).click();
		addItemDesciption.sendKeys(itemDes);
		saveItemButton.click();
	}

	public void deleteAnItem(String name) throws InterruptedException {
		if (!filterNameBox.isDisplayed()) {
			Thread.sleep(10000);
			utils.waitUntilElementToBeClickable(filterButton);
			filterButton.click();
			utils.waitUntilElementVisible(filterNameBox);
			utils.actionsSendKeys(filterNameBox, name);
		}

//		itemNameInTheItemsTable.findElement(
//				By.xpath(String.format(itemNameInTheItemsTable.getAttribute("xpath"), name))).isDisplayed();
		Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//a[text()='" + name + "']")).isDisplayed());

		utils.waitUntilElementToBeClickable(filterItem3dot);
		Thread.sleep(2000);
		utils.actionsClick(filterItem3dot);
		// filterItem3dot.click();
		utils.waitUntilElementToBeClickable(filter3dotDeleteBtn);
		utils.actionsClick(filter3dotDeleteBtn);
		// filter3dotDeleteBtn.click();
		utils.waitUntilElementToBeClickable(itemDeleteOkayBtn);
		// itemDeleteOkayBtn.click();
		utils.actionsClick(itemDeleteOkayBtn);
	}

}
