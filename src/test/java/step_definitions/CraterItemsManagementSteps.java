package step_definitions;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CraterCommonPage;
import pages.CraterItemsPage;
import pages.LoginPage;
import utilities.AmazonUtils;
import utilities.BrowserUtils;
import utilities.DButils;
import utilities.DataReader;
import utilities.Driver;

public class CraterItemsManagementSteps {

	LoginPage loginPage = new LoginPage();
	CraterItemsPage itemsPage = new CraterItemsPage();
	CraterCommonPage commonPage = new CraterCommonPage();
	BrowserUtils utils = new BrowserUtils();
	AmazonUtils amazonUtils = new AmazonUtils();
	DButils dbutil = new DButils();
	
	static String itemName;
	static int itemId;
	static List<String> list;
	
	@Given("As an entity user, I am logged in")
	public void as_an_entity_user_i_am_logged_in() {
		Driver.getDriver().get(DataReader.getProperty("appUrl"));
		loginPage.login();
	}
	@Given("I navigate to Items tab")
	public void i_navigate_to_items_tab() {
		commonPage.itemsLink.click();
		Assert.assertTrue(itemsPage.itemsPageHeaderText.isDisplayed());
		
	}
	@When("I click on the Add Item button")
	public void i_click_on_the_add_item_button() {
		itemsPage.addItemButton.click();
	}
	@Then("I should be on item input page")
	public void i_should_be_on_item_input_page() {
		Assert.assertTrue(itemsPage.addItemPageHeaderText.isDisplayed());
	}
	@When("I provide item information name {string}, price {int}, unit {string}, and description {string}")
	public void i_provide_item_information_name_price_unit_and_description(String name, Integer price, String unit, String description) {
		itemName = name + utils.randomNumber();
		itemsPage.addItemName.sendKeys(itemName);
		itemsPage.addItemPrice.sendKeys(price.toString());
		itemsPage.addItemUnit.click();
		utils.waitUntilElementVisible(itemsPage.addItem_pc_unit);
		Driver.getDriver().findElement(By.xpath("//span[text()='"+unit+"']")).click();
		
		itemsPage.addItemDesciption.sendKeys(description);
	}
	@When("I click Save Item button")
	public void i_click_save_item_button() {
	   itemsPage.saveItemButton.click();
	}
	@Then("The Item is added to the Item list table")
	public void the_item_is_added_to_the_item_list_table() throws InterruptedException {
		if (!utils.isElementPresent(itemsPage.filterNameBox)) {
			utils.waitUntilElementToBeClickable(itemsPage.filterButton);
			itemsPage.filterButton.click();
			utils.waitUntilElementVisible(itemsPage.filterNameBox);
			utils.actionsSendKeys(itemsPage.filterNameBox, itemName);
		}
		Thread.sleep(2000);
		Assert.assertTrue(
				Driver.getDriver().findElement(By.xpath("//a[text()='"+itemName+"']")).isDisplayed());
	}
	
	// update item scenario steps
	
	@When("I select the item {string}")
	public void i_select_the_item(String name) {
		Driver.getDriver().findElement(By.xpath("//a[text()='"+itemName+"']")).click();
	}
	@When("I should be on item details page")
	public void i_should_be_on_item_details_page() {
	    Assert.assertTrue(itemsPage.editItemHeaderText.isDisplayed());
	}
	@When("I update the item price to {int} dollars")
	public void i_update_the_item_price_to_dollars(Integer price) {
		itemsPage.addItemPrice.clear();
		itemsPage.addItemPrice.sendKeys(price.toString());
	}
	@When("I click Update Item button")
	public void i_click_update_item_button() throws InterruptedException {
		Thread.sleep(500);
	    itemsPage.updateButton.click();
	    
	}
	@Then("the Item price is updated to {int} dollars")
	public void the_item_price_is_updated_to_dollars(Integer updatedPrice) {
		String itemXpath = "(//a[text()='"+itemName+"']//parent::td//following-sibling::td)[2]//span";
	    String itemPrice = Driver.getDriver().findElement(By.xpath(itemXpath)).getText();
	    System.out.println(itemPrice); //$ 800.00
	    String trimmedPrice = itemPrice.substring(2);
	    Assert.assertEquals(trimmedPrice, updatedPrice + ".00");
	}
	
	
	// data table item create steps
	
	@When("I provide item information to the fields")
	public void i_provide_item_information_to_the_fields(DataTable dataTable) {
		List<String> itemInfo = dataTable.asList();
		for ( String info : itemInfo) {
			System.out.println(info);
		}
		itemName = itemInfo.get(0);
		itemsPage.addItemName.sendKeys(itemInfo.get(0));
		itemsPage.addItemPrice.sendKeys(itemInfo.get(1));
		itemsPage.addItemUnit.click();
		utils.waitUntilElementVisible(itemsPage.addItem_pc_unit);
		Driver.getDriver()
		.findElement(By.xpath("//span[text()='"+ itemInfo.get(2) +"']")).click();
		itemsPage.addItemDesciption.sendKeys(itemInfo.get(3));
	}
	
	
	
	// item delete scenario 
	@When("I create an item with following information")
	public void i_create_an_item_with_following_information(DataTable dataTable) {
		list = dataTable.asList();
	    itemName = list.get(0) + utils.randomNumber();
	    itemsPage.createAnItem(itemName, list.get(1), list.get(2), list.get(3));
	}
	@When("I delete the item created above")
	public void i_delete_the_item_created_above() throws InterruptedException {
	   System.out.println(itemName);
	   itemsPage.deleteAnItem(itemName);
	}
	@Then("The item is no longer in the items list table")
	public void the_item_is_no_longer_in_the_items_list_table() throws InterruptedException{
//		Thread.sleep(2000);
		if (!itemsPage.filterNameBox.isDisplayed()) {
			Thread.sleep(2000);
			utils.waitUntilElementToBeClickable(itemsPage.filterButton);
			Thread.sleep(2500);
			itemsPage.filterButton.click();
			utils.waitUntilElementToBeClickable(itemsPage.filterNameBox);
			utils.actionsSendKeys(itemsPage.filterNameBox, itemName);
		}
		utils.waitUntilElementVisible(itemsPage.filterNoResultFoundMessage);
		Assert.assertTrue(itemsPage.filterNoResultFoundMessage.isDisplayed());
	}
	
	// item create and check in db steps
	@Then("I should be able to see the item in database")
	public void i_should_be_able_to_see_the_item_in_database() {
		String query = "SELECT name, price, unit_id, description FROM items where name='"+itemName+"';";
		System.out.println(query);
		List<String> itemInfo = dbutil.selectArecord(query);
		for (String string : itemInfo) {
			System.out.println(string);
		}
		
		Assert.assertEquals(itemName, itemInfo.get(0));
		
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).equals("pc")) {
				Assert.assertEquals(itemInfo.get(i), "11");
			} else {
				Assert.assertEquals(list.get(i), itemInfo.get(i));
			}
		}
	}
	
	// update item and check in db
	@When("I update the item price to {int}")
	public void i_update_the_item_price_to(Integer int1) {
	   String updateQuery = "UPDATE items SET price='"+ int1 +"' WHERE name='"+itemName+"';";
	   dbutil.updateRecord(updateQuery);
	}
	@Then("The item price has been updated to {int} in database")
	public void the_item_price_has_been_updated_to_in_database(Integer int1) {
		String query = "SELECT name, price, unit_id, description FROM items where name='"+itemName+"';";
	    List<String> itemInfo = dbutil.selectArecord(query);
	    System.out.println(itemInfo.get(1));
	    Assert.assertEquals(itemInfo.get(1), int1.toString());
	}
	
	
	// insert a record to database steps
	
	@When("I insert a record into database called {string}")
	public void i_insert_a_record_into_database(String name) {
	    itemName = name + utils.randomNumber();
	    itemId = utils.randomNumber() + 7;
	    
		String Query = 
	    		"INSERT INTO items VALUES('"+itemId+"', '"+itemName+"', 'Nice games', '5500', '1', '11', '2023-04-25 23:09:32', '2023-04-25 23:09:32', '4', '1', '0');";
       
		dbutil.insertRecord(Query);
	}
	@Then("Item should be listed in the items table on the UI")
	public void item_should_be_listed_in_the_items_table_on_the_ui() throws InterruptedException {
		Driver.getDriver().navigate().refresh();
		utils.waitUntilElementToBeClickable(itemsPage.filterButton);
		utils.actionsClick(itemsPage.filterButton);
		utils.waitUntilElementVisible(itemsPage.filterNameBox);
		utils.actionsSendKeys(itemsPage.filterNameBox, itemName);
		Thread.sleep(2000);
		Assert.assertTrue(
				Driver.getDriver().findElement(By.xpath("//a[text()='"+itemName+"']")).isDisplayed());
	}
	
	@When("I refresh the page")
	public void i_refresh_the_page() {
	    Driver.getDriver().navigate().refresh();
	}
	
	
	// delete item in db
	@When("I delete the item created above via db")
	public void i_delete_the_item_created_above_via_db() {
		System.out.println(itemId);
		String deleteQuery = "DELETE FROM items WHERE id='"+itemId+"';";
	    dbutil.deleteRecord(deleteQuery);
	}

}




