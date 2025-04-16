package tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import base.BaseTest;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;

public class CartTest extends BaseTest {
	HomePage homePage;
	ProductPage productPage;
	CartPage cartPage;
	LoginPage loginPage = new LoginPage(driver);

	@BeforeTest
	public void setup() throws IOException {

		testName="Cart Test";
		testDescription="Testing the Cart functionality with positive and negative cases";
		testAuthor="Madhu Mitha";
		testCategory="Smoke Testing";
	}
	
	public void loginFunctionCart() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginWithPropertyCredentials();

		waitForElementVisible(By.id("nameofuser"), 15);
		HomePage homePage = new HomePage(driver);

		driver.get("https://www.demoblaze.com/prod.html?idp_=1");

		//productpage.clickAddToCart();
		waitForSeconds(2);
		//String alertText = productpage.getAlertText();
        //productpage.acceptAlert();
	}

	// Valid Case : Add Item to Cart
	@Test(priority = 1)
	public void testAddToCart() {

		test = extent.createTest("testAddToCart");
		LoginPage loginPage = new LoginPage(driver);
		ProductPage productpage = new ProductPage(driver);
		CartPage cartPage = new CartPage(driver);

		loginFunctionCart();
		//handleAlertIfPresent(10); 
		//productPage.clickProductTitle();
		productpage.clickAddToCart();
		cartPage.goToCartButton();
		Assert.assertTrue(cartPage.getItemCount() > 0, "Cart should contains Items!");
		test.pass("Product added to cart");
	}

	// Valid Case : Remove Item from Cart
	@Test(priority = 2)
	public void removeItemFromCart() throws InterruptedException {
		test = extent.createTest("Remove Item From Cart");
		LoginPage loginPage = new LoginPage(driver);
		ProductPage productpage = new ProductPage(driver);
		CartPage cartPage = new CartPage(driver);

		loginFunctionCart();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		cartPage.goToCartButton();
		//cartPage.deletefirstItem();
		cartPage.clearAllItems(); // delete everything
	    Assert.assertEquals(cartPage.getItemCount(), 0, "Cart is not empty after removing all items");
	    
		Assert.assertEquals(cartPage.getItemCount(), 0, "Item was not Removed");
		waitForSeconds(5);
		System.out.println(cartPage.getItemCount());
		
		test.pass("All items deleted from cart successfully. Cart is empty.");
	}

	// Valid Case : Delete item from cart
	@Test(priority = 3)
	public void clearCartItem(){
		test = extent.createTest("Clear Cart Item");
		loginPage = new LoginPage(driver);
	    loginPage.loginWithPropertyCredentials();

	    waitForElementVisible(By.id("nameofuser"), 10);
	    homePage = new HomePage(driver);
	    productPage = new ProductPage(driver);
	    cartPage = new CartPage(driver);

	    // Add 2 items to cart
	    homePage.selectProduct("Samsung galaxy s6");
	    productPage.clickAddToCart();
	    waitForSeconds(2);
	    productPage.acceptAlert();

	    homePage.clickHomePageBtn();
	    
	    homePage.selectProduct("Nokia lumia 1520");
	    productPage.clickAddToCart();
	    waitForSeconds(2);
	    productPage.acceptAlert();

	    // Go to cart and verify items added
	    cartPage.goToCartButton();
	    waitForSeconds(2);
	    int initialItemCount = cartPage.getItemCount();
	    Assert.assertTrue(initialItemCount >= 2, "Cart should have at least 2 items before clearing");

	    // Delete all items one by one
	    while (cartPage.getItemCount() > 0) {
	        cartPage.deletefirstItem();
	        cartPage.deletefirstItem();
	        waitForSeconds(2); 
	    }

	    // Verify cart is empty
	    int finalItemCount = cartPage.getItemCount();
	    Assert.assertEquals(finalItemCount, 0, "Cart is not empty after clearing all items");

	    test.pass("Cart cleared successfully. All items removed.");
	}

	// Valid case : Total Price Calculation
	@Test(priority = 4)
	public void totalPriceCalculation() {
		test = extent.createTest("Total Price Calculation");
		loginPage = new LoginPage(driver);
        loginPage.loginWithPropertyCredentials();

        waitForElementVisible(By.id("nameofuser"), 10);
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);

        homePage.selectProduct("Samsung galaxy s6");
        productPage.clickAddToCart();
        waitForSeconds(2);
        productPage.acceptAlert();

        homePage.clickHomePageBtn();
        
        homePage.selectProduct("Nokia lumia 1520");
        productPage.clickAddToCart();
        waitForSeconds(2);
        productPage.acceptAlert();

        cartPage = new CartPage(driver);
        cartPage.goToCartButton();
        
		int total = cartPage.getTotalPrice();
		 Assert.assertTrue(total > 0, "Total price should be greater than 0");
		 
		 while (cartPage.getItemCount() > 0) {
		        cartPage.deletefirstItem();
		        waitForSeconds(2);
		 }
		 test.pass("Cart total calculated correctly");
	}

	// Invalid Case : Proceed to checkout without items
	@Test(priority = 5)
	public void proceedWithoutItems() {
		test = extent.createTest("Proceed Without Items");
		loginPage = new LoginPage(driver);
        loginPage.loginWithPropertyCredentials();
        waitForSeconds(2);
        cartPage = new CartPage(driver);
		cartPage.goToCartButton();
		waitForSeconds(5);
		Assert.assertEquals(cartPage.isPlaceOrderEnabled(), "Checkout Button should be disabled");
		//Assert.assertTrue(true);
	}
	
	// Invalid Case : Add item to cart without login
	@Test(priority = 6)
    public void testAddToCartWithoutLogin() {
		test = extent.createTest("Add Item without Login");
        driver.get("https://www.demoblaze.com/prod.html?idp_=1");
        productPage = new ProductPage(driver);

        productPage.clickAddToCart();
        waitForSeconds(2);
        String alertText = productPage.getAlertText();
        productPage.acceptAlert();

        cartPage = new CartPage(driver);
        cartPage.goToCartButton();
        Assert.assertTrue(alertText.toLowerCase().contains("login"), "Expected login alert");
        test.pass("Proper alert shown for unauthenticated add to cart");
    }

}
