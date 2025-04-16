package tests;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import base.BaseTest;
import pages.LoginPage;
import pages.ProductPage;
import pages.SignUpPage;
import utilis.Utility;

public class SignUpTest extends BaseTest{
	@BeforeTest
	public void setup() throws IOException {

		sheetname = "signupData";
		testName = "SignUp Test";
		testDescription = "Testing the login functionality with positive and negative cases";
		testAuthor = "Madhu Mitha";
		testCategory = "Smoke Testing";
	}
	
	@DataProvider(name="signupData")
	public Object[][] getSignUpData() throws Exception{
		    return Utility.getDataExcel("signupData");
		}

   @Test(priority = 1)
    public void verifySignupButtonVisibility() {
        SignUpPage signupPage = new SignUpPage(driver);
        waitForSeconds(5);
        Assert.assertTrue(signupPage.signupButtonVisible(), "Sign up button is not visible.");
        test.pass("Signuo Button Visible Successfully");
    }

    @Test(priority = 2)
    public void verifySignupModalOpens() {
    	SignUpPage signupPage = new SignUpPage(driver);
    	signupPage.clickSignupButton();
    	waitForSeconds(5);
        Assert.assertTrue(signupPage.signupModalDisplayed(), "Sign up modal did not open.");
        test.pass("Signuo Modal Opened Successfully");
    }
     
	@Test(priority =3, dataProvider = "signupData")
	public void testSignUppageMultipleData(String username, String password, String expectedMessage) throws InterruptedException {
		SignUpPage signupPage = new SignUpPage(driver);
	    test = extent.createTest("Signup Test - " + username);  
		signupPage = new SignUpPage(driver); //Extend webdriver from base class
		
		//Click signup button
		signupPage.clickSignupButton();
		signupPage.signUp(username,password); //perform signup
		
		//Handle alert for successful signup
		
		/*Alert alert = driver.switchTo().alert();
		String actualMessage = alert.getText();
		alert.accept();
		*/
		
		String actualMessage = signupPage.getSignUpAlertMessage();
		Assert.assertEquals(actualMessage, expectedMessage, "Signup test failed!");
		test.pass("Signup with valid and invalid Credentials are Successfull");
		test.pass("Signup with Invalid Credentials are Present in the console");
		//Assert.assertEquals(actualMessage, "Signup successfull.");
		
		
	}
	

}
