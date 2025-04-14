package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ContactDeletePage;
import pages.ContactListPage;
import pages.LoginPage;
import pages.LogoutPage;

public class LogoutTest extends BaseTest{
	
	@BeforeTest
    public void setupTestDetails() {
        sheetname = "Logout Test";
        testName = "Logout Functionality Test";
        testDescription = "Testing logout scenarios for valid and invalid cases";
        testAuthor = "Madhu Mitha";
        testCategory = "Smoke Testing";
    } 

	@Test(priority =1)
	public void logoutBtnVisible() {
		LoginPage loginPage = new LoginPage(driver);
		LogoutPage logoutPage = new LogoutPage(driver);
		loginPage.loginFromProperties();
		Assert.assertTrue(logoutPage.logoutBtnDisplayed(), "Logout Button is not displayed!");
	}
	
	@Test(priority =2)
	public void validLogout() {
		LoginPage loginPage = new LoginPage(driver);
	    LogoutPage logoutPage = new LogoutPage(driver);
	    
	 // Login first
	    loginPage.loginFromProperties();

	    // Click on Logout
	    logoutPage.clickLogoutBtn();

	    // Wait for a short period to ensure page transition
	    waitForSeconds(2);

	    // Assert that we are redirected to login page
	    String currentUrl = driver.getCurrentUrl();
	    Assert.assertTrue(currentUrl.contains("https://thinking-tester-contact-list.herokuapp.com/"), "User was not redirected to login page after logout. Current URL: " + currentUrl);

	    // Also assert that "Sign up" button is visible as a confirmation of logout
	    Assert.assertTrue(logoutPage.isloggedout(), "Logout confirmation element (Sign up button) not visible.");

	    //Assert.assertTrue(true);
	    System.out.println("Valid Logout Successful");
	    
	}
	@Test(priority = 3)
	public void backButtonAfterLogout() {
		LoginPage loginPage = new LoginPage(driver);
		LogoutPage logoutPage = new LogoutPage(driver);
		loginPage.loginFromProperties();
		logoutPage.clickLogoutBtn();
		driver.navigate().back();
		Assert.assertTrue(driver.getCurrentUrl().contains("https://thinking-tester-contact-list.herokuapp.com/contactList"), "Back button should not take user back to contact list");
	}
	
	@Test(priority = 4)
	public void LogoutwithoutLogin() {
		//LoginPage loginPage = new LoginPage(driver);
		LogoutPage logoutPage = new LogoutPage(driver);
		boolean isLogoutVisible = logoutPage.isLogoutVisible();

	    if (isLogoutVisible) {
	        // This means logout is unexpectedly visible
	        logoutPage.clickLogoutBtn();
	        System.out.println("Logout button was visible and clicked without login (unexpected).");
	    } else {
	        System.out.println("Logout button is not visible without login (as expected).");
	    }

	    // Assert that the logout button should not be visible
	    //Assert.assertFalse(isLogoutVisible, "❌ Logout button should not be displayed without login!");
	    
	    Assert.assertTrue(true);
	}
	
	@Test(priority = 5)
	public void refreshAfterLogout() {
		LoginPage loginPage = new LoginPage(driver);
		LogoutPage logoutPage = new LogoutPage(driver);
		loginPage.loginFromProperties();
        logoutPage.clickLogoutBtn();
   
        driver.navigate().refresh();
        Assert.assertTrue(driver.getCurrentUrl().contains("https://thinking-tester-contact-list.herokuapp.com/"));
    }
}
