package tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import base.BaseTest;
import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import utilis.Utility;

public class CheckoutTest extends BaseTest {

	@BeforeTest
	public void setup() throws IOException {

		sheetname = "CheckoutPage";
		testName = "CheckoutPage Test";
		testDescription = "Testing the Checkout functionality with positive and negative cases";
		testAuthor = "Madhu Mitha";
		testCategory = "Smoke Testing";
	}

	@DataProvider(name = "CheckoutPage")
	public Object[][] getCheckoutData() throws Exception {
		return Utility.getDataExcel("CheckoutPage");
	}

	public void loginFunctionCheckOut() {
		LoginPage loginPage = new LoginPage(driver);
		ProductPage productpage = new ProductPage(driver);
		CartPage cartPage = new CartPage(driver);
		HomePage homePage = new HomePage(driver);

		// Login to Application
		loginPage.loginWithPropertyCredentials();

		// Wait for Welcome Name to visible
		waitForElementVisible(By.id("nameofuser"), 8);

		// Search for Item in the home Page
		homePage.selectProduct("Samsung galaxy s6");
		
		// Click add to cart button
		productpage.clickAddToCart();
		waitForSeconds(2);
		productpage.acceptAlert();

		// Go to Cart to Place order
		cartPage.goToCartButton();
		waitForSeconds(5);
		
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.PlaceOrderBtn();
	}

	@Test(priority = 1)
	public void placeOrderModalOpens() throws InterruptedException {
		loginFunctionCheckOut();

		waitForSeconds(2);
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		//checkoutPage.PlaceOrderBtn();
		//waitForSeconds(4);
		Assert.assertTrue(checkoutPage.CheckoutModalDisplayed(), "Checkout Modal did not open");
		checkoutPage.CheckoutModalDisplayed();
	}

	@Test(priority = 2)
	public void checkOutFieldPresent() {
		loginFunctionCheckOut();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		waitForSeconds(5);
		Assert.assertTrue(checkoutPage.checkoutFormDisplayed(), "Checkout form fields are not properly displayed");
		
		Map<String, Boolean> presence = checkoutPage.areCheckoutFieldsPresent();
	    for (Map.Entry<String, Boolean> field : presence.entrySet()) {
	        Assert.assertTrue(field.getValue(), field.getKey() + " field not visible");
	        System.out.println(field.getKey() + " field is present");
	    }
	    
		List<String> placeholders = checkoutPage.getCheckoutFieldPlaceholders();
	    System.out.println("Field placeholders: " + placeholders);
	}

	
	@Test(priority = 3, dataProvider = "CheckoutPage")
	public void confirmPurchaseTest(String name, String country, String city, String card, String month, String year, String Expectedresult) {
		test = extent.createTest("Confirm Purchase Test");

		loginFunctionCheckOut();
		HomePage homePage = new HomePage(driver);
		CheckoutPage checkoutPage = new CheckoutPage(driver);

		checkoutPage.fillCheckoutDetails(name, country, city, card, month, year);
		checkoutPage.clickPurchase();
		waitForSeconds(5);
		
		if (Expectedresult.equalsIgnoreCase("success")) {
	        if (checkoutPage.isPurchaseSuccessful()) {
	            String confirmation = checkoutPage.getPurchaseDetails();
	            System.out.println("Purchase Confirmed: \n" + confirmation);
	            Assert.assertTrue(confirmation.contains("Amount"), "Purchase amount not found in confirmation.");
	            checkoutPage.closeConfirmation();
	            test.pass("Purchase completed successfully and confirmation verified.");
	        } else {
	            Assert.fail("Expected success but confirmation not displayed.");
	        }
	    } else {
	        String alertMsg = checkoutPage.getAlertMessageIfAny();
	        if (alertMsg != null && !alertMsg.isEmpty()) {
	            System.out.println("Validation Alert: " + alertMsg);
	            Assert.assertTrue(alertMsg.toLowerCase().contains(Expectedresult.toLowerCase()), "Expected validation error not shown.");
	            test.pass("Validation alert matched expected message.");
	        } else {
	            Assert.fail("Expected failure but no alert shown.");
	        }
	    }

	}

	@Test(priority = 4)
	public void emptyFieldPurchase() {

		test = extent.createTest("emptyFieldPurchase Test");
		LoginPage loginPage = new LoginPage(driver);
		
		// Step 1 : Login to the Application
	    loginPage.loginWithPropertyCredentials();

	    waitForElementVisible(By.id("nameofuser"), 10);
	    
	    // Step 2: Go to Cart
	    CartPage cartPage = new CartPage(driver);
	    cartPage.goToCartButton();
	    waitForSeconds(2);
	    
	    while (cartPage.getItemCount() > 0) {
	        cartPage.deletefirstItem();
	        cartPage.deletefirstItem();
	        waitForSeconds(2); 
	    }
	    
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		waitForSeconds(2);
		checkoutPage.PlaceOrderBtn();
		waitForSeconds(5);
		checkoutPage.clickPurchase();
		waitForSeconds(5);
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "Please fill out Name and Creditcard.", "Error Message not displayed");
		alert.accept();
	}

	@Test(priority = 6)
	public void testCheckoutWithoutAddingItemsFails() {
		LoginPage loginPage = new LoginPage(driver);
		ProductPage productpage = new ProductPage(driver);
		CartPage cartPage = new CartPage(driver);
		HomePage homePage = new HomePage(driver);
		CheckoutPage checkoutPage = new CheckoutPage(driver);

		loginPage.loginWithPropertyCredentials();
		cartPage.goToCartButton();
		while (cartPage.getItemCount() > 0) {
			cartPage.deletefirstItem();
			waitForSeconds(2);
		}
		checkoutPage.PlaceOrderBtn();
		checkoutPage.clickPurchase();
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "Cart is empty!", "Incorrect error message!");
		alert.accept();
	}

}
