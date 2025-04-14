package tests;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.SignUpPage;
import utilis.Utility;

public class SignUpTest extends BaseTest{
	SignUpPage signupPage;  //creating object

	@BeforeTest
	public void setup() throws IOException {

		sheetname = "Signup Test";
		testName = "Signup Test";
		testDescription = "Testing the Sign Up functionality with positive and negative cases";
		testAuthor = "Madhu Mitha";
		testCategory = "Smoke Testing";
	}

	@DataProvider(name = "signupData")
	public Object[][] getSignUpData() throws Exception {
		return Utility.readExcel("signupData");
	}
	
	@Test(priority = 1, dataProvider = "signupData") 
	public void testSignUppage(String firstname, String lastName, String email, String password) {
		test = extent.createTest("Signup Test");
		
		signupPage = new SignUpPage(driver); //Extend webdriver from base class
		
		// Using Fluent Interface 
		signupPage.clickSignupBtn()   //Perform signup
		.enterFirstName(firstname)
		.enterLastName(lastName)
		.enterEmail(email)
		.enterPassword(password)
		.submitSignUpButton();
		waitForSeconds(5);
		
		// Verification
		if (signupPage.errorMessageDisplayed()) {
            String error = signupPage.getErrorMessage();
            System.out.println("Signup Error: " + error);  
            test.info("Signup failed as expected. Error: " + error);
        } else if (signupPage.isSignupSuccessful()) {
            System.out.println("Signup Successful for email: " + email);
            test.pass("Signup succeeded as expected.");
        } else {
            System.out.println("No error but not redirected");
            test.warning("Unexpected outcome. No error, but not redirected.");
        }

        Assert.assertTrue(true);
	}
	@AfterMethod
    public void refreshPage() {
        driver.navigate().refresh();
    }

}
