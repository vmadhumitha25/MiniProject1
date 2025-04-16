package tests;

import java.io.IOException;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import base.BaseTest;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import utilis.Utility;

@Listeners(utilis.Listners.class)
public class ProductPageTest extends BaseTest{

	LoginPage login;
	ProductPage productpage;
	@BeforeTest
	public void setup() throws IOException {
		
		testName="ProductPage Test";
		testDescription="Testing the Product functionality with positive and negative cases";
		testAuthor="Madhu Mitha";
		testCategory="Smoke Testing";
	}
	
	@Test(priority = 1)
	public void testProductDetails() {

		HomePage homePage = new HomePage(driver);
		productpage = new ProductPage(driver);
		login = new LoginPage(driver);
		
		waitForSeconds(3);
        login.loginWithPropertyCredentials();

        waitForElementVisible(By.id("nameofuser"), 15);

        String actualText = homePage.getWelcomeUsernameText(); // "Welcome MadhuV"
        String expectedText = "Welcome MadhuV"; 
		
		String expectedproductName = "Samsung galaxy s6";
		String expectedProductPrice = "$360 *includes tax";
		String expectedDescription = "Product description\r\n"
				+ "The Samsung Galaxy S6 is powered by 1.5GHz octa-core Samsung Exynos 7420 processor and it comes with 3GB of RAM. The phone packs 32GB of internal storage cannot be expanded.";
		
		 // Click on "Samsung galaxy s6"
        productpage.clickProductTitle();
        waitForSeconds(5);
		Assert.assertEquals(productpage.getProductName(), expectedproductName, "Product Name does not match");
		test.pass("Product name is correct");
		
		Assert.assertEquals(productpage.getProductPrice(), expectedProductPrice, "Product price does not match");
		test.pass("Product Price is correct");
		
		Assert.assertEquals(productpage.getProductDescription().contains("Samsung Galaxy S6"), expectedDescription, "Product Description does not match");
		test.pass("Product Description is correct");
		
		Assert.assertTrue(productpage.isAddToCartDisplayed(), "Add to cart button is missing!");
		test.pass("Add to Cart button is visible");
		
	}
	
	 @Test(priority = 2)
	    public void testProductDetailsDisplayed() {
	        // Navigate to a specific product URL
	        driver.get("https://www.demoblaze.com/prod.html?idp_=1");

	        productpage = new ProductPage(driver);
	        Assert.assertTrue(productpage.getProductName().length() > 0, "Product title missing");
	        Assert.assertTrue(productpage.getProductPrice().contains("$"), "Product price not displayed");
	        Assert.assertTrue(productpage.getProductDescription().length() > 0, "Description missing");
	        Assert.assertTrue(productpage.isImageDisplayed(), "Image not visible");

	        test.pass("Product details displayed properly");
	    }

	    @Test(priority = 3)
	    public void testAddToCartWithoutLogin() throws InterruptedException {
	        driver.get("https://www.demoblaze.com/prod.html?idp_=1");
	        productpage = new ProductPage(driver);
	        productpage.clickAddToCart();
	        Thread.sleep(2000); // wait for alert
	        String alertText = productpage.getAlertText();
	        productpage.acceptAlert();

	        Assert.assertTrue(alertText.contains("login"), "Alert for unauthenticated add to cart not shown");
	        test.pass("Add to cart alert shown for unauthenticated user");
	        test.fail("Add to cart redirected to product page");
	    }

	    @Test(priority = 4)
	    public void testAddToCartAfterLogin() throws InterruptedException {
	    	LoginPage loginPage = new LoginPage(driver); 
	    	loginPage.loginWithPropertyCredentials();

	    	Utility util = new Utility();
	        HomePage homePage = new HomePage(driver);

	        driver.get("https://www.demoblaze.com/prod.html?idp_=1");
	        ProductPage product = new ProductPage(driver);

	        product.clickAddToCart();
	        Thread.sleep(2000);
	        String alertText = product.getAlertText();
	        product.acceptAlert();

	        Assert.assertTrue(alertText.contains("Product added"), "Add to cart success alert missing");
	        test.pass("Product added to cart successfully");
	    }
	    
	    @Test(priority = 4)
	    public void verifyBackToHomeFunctionality() {
	    	HomePage homePage = new HomePage(driver);
			productpage = new ProductPage(driver);
			login = new LoginPage(driver);
	   
			productpage.clickCategory("Monitors");
			productpage.selectProductByName("Apple monitor 24");
	        homePage.clickHomePageBtn();

	        Assert.assertTrue(driver.getCurrentUrl().contains("index.html"));
	    }
	

}
