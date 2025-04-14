package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ContactAddPage;
import pages.LoginPage;
import utilis.Utility;


public class ContactAddTest extends BaseTest  {
	
	LoginPage loginPage;
    ContactAddPage contactPage;

    @BeforeTest
	public void setup() throws IOException {

		sheetname = "Contact Add Test";
		testName = "Contact Add Test";
		testDescription = "Testing the Contact Add functionality";
		testAuthor = "Madhu Mitha";
		testCategory = "Smoke Testing";
	}


	@DataProvider(name = "ContactData")
	public Object[] [] contactData() throws Exception {
		return Utility.readExcel("ContactData");
	}
	
	@Test(priority = 1, dataProvider = "ContactData")
	public void testAddContact(String firstname, String lastname, String birthdate, String email, String phone, 
			String streetAddress1, String streetAddress2, String city, String state, String code, String country, String ExpectedResult) {
		test = extent.createTest("Add Contact Test");
		ContactAddPage contactpage = new ContactAddPage(driver);
		loginPage = new LoginPage(driver);
        loginPage.loginFromProperties();
       // Assert.assertTrue(loginPage.loginSuccessful(), "Login Failed!");

        System.out.println("Email from properties: " + Utility.getProperty("user.email"));
        System.out.println("Password from properties: " + Utility.getProperty("user.password"));
        
		contactpage.clicklAddContactBtn();
		contactpage.addContact(firstname, lastname, birthdate, email, phone, streetAddress1, 
				streetAddress2, city, state, code, country, ExpectedResult);
		waitForSeconds(5);
		if (ExpectedResult.equalsIgnoreCase("Success")) {
	        if (driver.getCurrentUrl().contains("contactList")) {
	            System.out.println("Contact added successfully: " + email);
	        } else {
	            System.out.println("Expected success but failed to stay on contactList.");
	            Assert.fail("Contact was not added successfully.");
	        }
	    } else {
	        if (contactpage.isErrorDisplayed()) {
	            String errorMessage = contactpage.getErrorMessage();
	            System.out.println("Error Message: " + errorMessage);
	            Assert.assertTrue(true);  // Mark test as passed for expected error
	        } else {
	            System.out.println("Expected error but none displayed.");
	            Assert.fail("Expected error message not shown.");
	        }
	    }

	    loginPage.logoutButton();
	}
	
	
}
