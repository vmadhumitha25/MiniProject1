package tests;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utilis.Utility;

public class LoginTest extends BaseTest {

	LoginPage loginpage;
	
	@BeforeTest
	public void setup() throws IOException {

		sheetname = "loginData";
		testName="Login Test";
		testDescription="Testing the login functionality with positive and negative cases";
		testAuthor="Madhu Mitha";
		testCategory="Smoke Testing";
	}
	
	@DataProvider(name = "loginData")
	public Object[][] LoginData() throws Exception {
	    return Utility.getDataExcel("loginData");
	}
	
	@Test(priority = 1)
    public void verifyLoginButtonVisibile() {
        LoginPage loginpage = new LoginPage(driver);
        Assert.assertTrue(loginpage.isLoginButtonVisible(), "Login button is not visible.");
        test.pass("Login Button Visible Successfully");
    }

    @Test(priority = 2)
    public void verifySignupModalOpens() throws InterruptedException {
    	LoginPage loginpage = new LoginPage(driver);
    	loginpage.clickLoginButton();
    	//waitForSeconds(5);
        Assert.assertTrue(loginpage.isLoginModalVisible(), "Login modal did not open.");
        test.pass("Login modal opened successfully.");
    }
    
	@Test(priority = 3, dataProvider = "loginData")
	public void testLogin(String username, String password, String expectedmessage){
		loginpage = new LoginPage(driver);
		test = extent.createTest("Login Test - " + username +password + expectedmessage);  
		loginpage.performLogin(username, password);
		//loginpage.submitLogin();
		//waitForElementVisible(By.id("nameofuser"), 5);

		if (expectedmessage.equalsIgnoreCase("success")) {
			waitForElementVisible(By.id("nameofuser"), 5);
			Assert.assertTrue(loginpage.isLogooutButtonDisplayed(), "Login Failed - Logout button not visible.");

			String welcomeText = loginpage.getWelcomeText();
			System.out.println("Login Success - Welcome Message: " + welcomeText);
			Assert.assertTrue(welcomeText.contains(username), "Welcome message doesn't contain username.");
		} else {
			// Handle Alert for failure
			String actualAlert = loginpage.getAlertMessage();
			waitForSeconds(5);
			System.out.println("Login Failed - Alert: " + actualAlert);
			Assert.assertTrue(actualAlert.contains(expectedmessage), "Alert message doesn't match expected.");
		}
		test.pass("Login validated with positive and negative cases.");
		
	}
	
}
