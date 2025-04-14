package tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ContactListPage;
import pages.LoginPage;

public class ContactDisplayTest extends BaseTest {

	LoginPage loginpage;
	ContactListPage contactlistPage;

	@BeforeTest
	public void setup() throws IOException {
		sheetname = "ContactDisplay";
		testName = "Contact Display Test";
		testDescription = "Testing display of contacts with valid and invalid cases";
		testAuthor = "Madhu Mitha";
		testCategory = "Regression";
	}

	@Test(priority = 1)
    public void testPrintAllContacts() {
		
		test = extent.createTest("Contact Print Test");
		LoginPage loginpage = new LoginPage(driver);
		ContactListPage contactlistPage = new ContactListPage(driver);

		loginpage.loginFromProperties();

		    for (WebElement row : contactlistPage.contactRows) {
		        System.out.println("Contact Info: " + row.getText());
		    }
        // Print contacts to console	
        System.out.println("===== Contact List =====");
        contactlistPage.printAllContacts();
    }
	
	@Test(priority = 2)
	public void viewAndVerifyContactList(String firstname, String lastname) throws IOException {

		test = extent.createTest("Contact Displayed Test");
		LoginPage loginpage = new LoginPage(driver);
		ContactListPage contactlistPage = new ContactListPage(driver);

		loginpage.loginFromProperties();

		boolean displayed = contactlistPage.isContactDisplayed(firstname, lastname);
        if (displayed) {
            test.pass("Contact displayed: " + firstname + " " + lastname);
            System.out.println("Contact displayed successfully: " + firstname + " " + lastname);
        } else {
            test.fail("Expected contact was not displayed.");
            Assert.fail("Expected contact was not displayed.");
        }

		// Optionally check if contacts are sorted alphabetically by last name
		Assert.assertTrue(contactlistPage.sortedByName(), "Contacts are not sorted alphabetically by last name.");

		// Validate email format
		Assert.assertTrue(contactlistPage.areAllEmailsValid(), "Invalid email found in the contact list.");

		// Take screenshot of contact list page
		contactlistPage.takeContactListScreenshot("ContactListPage");

		System.out.println("Contact list viewed and verified successfully.");
	}
	@Test(priority = 3)
    public void testContactDisplayed() {
		test = extent.createTest("Contact Displayed Full name Test");
		LoginPage loginpage = new LoginPage(driver);
		ContactListPage contactlistPage = new ContactListPage(driver);

		loginpage.loginFromProperties();
        
        String contactName = "Bharathi Magesh";
        boolean isDisplayed = contactlistPage.contactDisplayed(contactName);
        waitForSeconds(5);
        System.out.println("Checking if contact '" + contactName + "' is displayed: " + isDisplayed);
        Assert.assertTrue(isDisplayed, "Contact not found in the list.");
    }

    @Test(priority = 4)
    public void testContactInfoByName() {
    	test = extent.createTest("Contact Info By Name Test");
		LoginPage loginpage = new LoginPage(driver);
		ContactListPage contactlistPage = new ContactListPage(driver);

		loginpage.loginFromProperties();

        String name = "mimi ju";
        String info = contactlistPage.contactInfoByName(name);
        
        System.out.println("Info for " + name + ": " + info);
        Assert.assertNotEquals(info, "Contact not found", "Contact not found for given name.");
    }

    @Test(priority = 5)
    public void testContactsSortedByName() {
    	test = extent.createTest("Contact Sorting Test");
		LoginPage loginpage = new LoginPage(driver);
		ContactListPage contactlistPage = new ContactListPage(driver);

		loginpage.loginFromProperties();
        
        boolean isSorted = contactlistPage.sortedByName();
        System.out.println("Are contacts sorted by name? " + isSorted);
        Assert.assertTrue(isSorted, "Contacts are not sorted by name.");
    }

    @Test(priority = 6)
    public void testAllEmailsValid() {
    	test = extent.createTest("All Email Id are valid Test");
		LoginPage loginpage = new LoginPage(driver);
		ContactListPage contactlistPage = new ContactListPage(driver);

		loginpage.loginFromProperties();
        
        boolean allValid = contactlistPage.areAllEmailsValid();
        System.out.println("Are all emails valid? " + allValid);
        Assert.assertTrue(allValid, "Some emails are invalid.");
    }
}
