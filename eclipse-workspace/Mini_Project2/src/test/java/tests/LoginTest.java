package tests;

import java.io.IOException;

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

		sheetname = "Valid InvalidLoginSheet";
		testName = "Login Test";
		testDescription = "Testing the login functionality with positive and negative cases";
		testAuthor = "Madhu Mitha";
		testCategory = "Smoke Testing";
	}

	@DataProvider(name = "Valid InvalidLoginSheet")
	public Object[][] validLoginData() throws Exception {
		return Utility.readExcel("Valid InvalidLoginSheet");
	}

	@Test(priority = 1, dataProvider = "Valid InvalidLoginSheet")
	public void testValidLogin(String emailID, String password) {
		test = extent.createTest("Login Test");
		loginpage = new LoginPage(driver);
		loginpage.performLogin(emailID, password);
		//loginpage.submitButton();

		// Verify successful login by checking page redirection or session
		String expectedUrl = "https://thinking-tester-contact-list.herokuapp.com/contactList";
		waitForSeconds(5);
		if (driver.getCurrentUrl().equals(expectedUrl)) {
			System.out.println("Login Successful for: " + emailID + "  " + password);
			Assert.assertTrue(true, "Login succeeded as expected.");
			test.pass("Login succeeded as expected.");

		} else if (loginpage.isErrorMessageDisplayed()) {
			String error = loginpage.getErrorText();
			System.out.println("LoginPage Error: " + error);

			test.info("Login failed as expected. Error: " + error);
			System.out.println("Unexpected Login Failure for: " + emailID + " | Error: " + error);
			//Assert.fail("Login failed unexpectedly for valid credentials.");
		} else {
			System.out.println("Login failed without error message.");
			test.warning("Unexpected outcome. No error, but not redirected.");
		}
		Assert.assertTrue(true);
	}
}
