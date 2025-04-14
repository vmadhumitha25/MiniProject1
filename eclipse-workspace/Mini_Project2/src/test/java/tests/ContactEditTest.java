package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ContactEditPage;
import pages.ContactListPage;
import pages.LoginPage;
import utilis.Utility;

public class ContactEditTest extends BaseTest {
	
	  private ContactListPage contactListPage;
	  private ContactEditPage contactEditPage;
	    
	@BeforeTest
	public void setup() throws IOException {

		sheetname = "Contact Edit Test";
		testName = "Contact Edit Test";
		testDescription = "Testing the Contact Edit functionality with positive and negative cases";
		testAuthor = "Madhu Mitha";
		testCategory = "Regression Testing";
		
		/* Login only once before all tests
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginFromProperties();
*/
        // Navigate to contact list
        ContactListPage contactListPage = new ContactListPage(driver);
        ContactEditPage contactEditPage = new ContactEditPage(driver);
	}

	@DataProvider(name = "ContactEditData")
	public Object[][] getContactData() throws Exception {
		return Utility.readExcel("EditContact");
	}

	@Test(dataProvider = "ContactEditData")
	public void testEditContact(String fname, String lname, String email, String phone, String birthdate,String Expected) {
         
		test = extent.createTest("Edit Contact Test" +fname+ " " +lname);
		
		// Login only once before all tests
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginFromProperties();
        
        // Object Creation
        ContactListPage contactListPage = new ContactListPage(driver);
        ContactEditPage contactEditPage = new ContactEditPage(driver);
		
        // Navigate to and open the contact
        contactListPage.openFirstContact();
       
        contactEditPage.clickEditContact();
       // contactEditPage.clickContact(fname);
        contactEditPage.editContact(fname, lname, email, phone, birthdate);
        contactEditPage.submitEditedContact();
        boolean isSuccess = contactEditPage.isEditSuccessful();

        System.out.println("Edited Contact: " + fname + " " + lname + ", Success: " + isSuccess);

     // Check for error or validation message
        String error = contactEditPage.getErrorMessage();
        if (error != null && !error.isEmpty()) {
            System.out.println("Error Message Displayed: " + error);
        } else {
            System.out.println("No error message displayed when trying to save empty contact.");
        }
        
		if (Expected.equalsIgnoreCase("Success")) {
			Assert.assertTrue(contactEditPage.isEditSuccessful(), "Edit should be successful");
		} else {
			Assert.assertFalse(contactEditPage.isEditSuccessful(), "Edit should fail with invalid data");
		}
	}
}
