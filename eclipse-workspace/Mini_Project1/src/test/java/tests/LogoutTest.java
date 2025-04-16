package tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.HomePage;
import pages.LoginPage;
import pages.LogoutPage;

public class LogoutTest extends BaseTest {
	HomePage homePage;
	LoginPage loginPage;
	LogoutPage logoutPage;

	@BeforeTest
	public void setup() throws IOException {

		testName = "Logout Test";
		testDescription = "Testing the Logout functionality with positive and negative cases";
		testAuthor = "Madhu Mitha";
		testCategory = "Smoke Testing";
	}

	@Test(priority = 1)
	public void logoutBtnVisible() {
		test = extent.createTest(" Logout Button Visible in the Application after Login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginWithPropertyCredentials();

		waitForSeconds(5);
		LogoutPage logoutPage = new LogoutPage(driver);
		Assert.assertTrue(logoutPage.logoutBtnDisplayed(), "Logout Button is not displayed!");
		test.pass("Logout Button Visible");
	}

	@Test(priority = 2)
	public void validLogout() {
		test = extent.createTest("Valid Logout from the Application");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		LoginPage loginPage = new LoginPage(driver);

		// Login to the Application
		loginPage.loginWithPropertyCredentials();
		// Wait for Welcome Name to visible
		waitForElementVisible(By.id("nameofuser"), 8);

		// Initialize the page and logout from the Application
		LogoutPage logoutPage = new LogoutPage(driver);
		logoutPage.clickLogoutBtn();
		Assert.assertTrue(logoutPage.loginBtnDisplayed(), "User is not redirect to home page after logout");
		test.pass("Valid Logout Function");
	}

	@Test(priority = 3)
	public void accessCartMenu() {
		test = extent.createTest("Access Cart Menu after Logout from the Application");
		LoginPage loginPage = new LoginPage(driver);
		// Login to the Application
		loginPage.loginWithPropertyCredentials();
		// Wait for Welcome Name to visible
		waitForElementVisible(By.id("nameofuser"), 8);

		// Initialize the page and logout from the Application
		LogoutPage logoutPage = new LogoutPage(driver);
		logoutPage.clickLogoutBtn();

		waitForSeconds(4);
		// Try accessing Cart after logout
		logoutPage.cartmenu();

		String expectedUrl = "https://www.demoblaze.com/cart.html";
		Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "User is not redirected to HomePage ");
		test.pass("Logout Function and Access Cart");
	}

	@Test(priority = 4)
	public void sessionclearAfterLogout() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.performLogin("MadhuV", "Madhu@123");
		waitForSeconds(5);
		LogoutPage logoutPage = new LogoutPage(driver);
		logoutPage.clickLogoutBtn();
		driver.navigate().refresh();
		Assert.assertTrue(logoutPage.loginBtnDisplayed(), "Session is not cleared after logout");
	}

	@Test(priority = 5)
	public void LogoutwithoutLogin() {
		test = extent.createTest("Logout Without Login Test");

		WebElement logoutBtn = driver.findElement(By.id("logout2"));
		if (logoutBtn.isDisplayed() && logoutBtn.isEnabled()) {
			logoutBtn.click();
			Assert.fail("Logout should not be clickable without login.");
		} else {
			System.out.println("Logout button is not interactable as expected (not logged in).");
			test.pass("Logout not shown without login - Passed.");
		}
	}

	@Test(priority = 6)
	public void testMultipleLogouts() {
		test = extent.createTest("Multiple Logout Button clicks");
		
		// Login to the Application
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginWithPropertyCredentials();
		
		waitForElementVisible(By.id("nameofuser"), 8);

		LogoutPage logoutPage = new LogoutPage(driver);
		logoutPage.clickLogoutBtn();
		
		// Wait a moment to allow DOM to settle
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login2"))); // Wait for Login button
	    
	 // Try clicking logout again (button will likely be gone)
	    try {
	    	logoutPage.clickLogoutBtn(); // This will internally re-fetch the element
	    } catch (Exception e) {
	        test.info("Second logout click skipped as the button is no longer available.");
	    }
	   
	    // Final check: Login button should be visible
	    boolean loginVisible = driver.findElement(By.id("login2")).isDisplayed();
	    Assert.assertTrue(loginVisible, "Login button should be visible after logout.");

	    test.pass("Handled multiple logout clicks successfully");
	}
}
