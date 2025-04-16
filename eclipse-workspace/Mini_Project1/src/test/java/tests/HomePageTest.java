package tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.HomePage;
import pages.LoginPage;

public class HomePageTest extends BaseTest{
	
	@BeforeTest
	public void setup() throws IOException {
		testName="Home Page Test";
		testDescription="Testing the login functionality with positive and negative cases";
		testAuthor="Madhu Mitha";
		testCategory="Smoke Testing";
	}
	
    @Test(priority = 1)
    public void testWelcomeUsernameDisplayed() {
    	 test = extent.createTest("Verify Welcome Username is Displayed");
    	 
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWithPropertyCredentials();

        waitForElementVisible(By.id("nameofuser"), 15);
        HomePage homePage = new HomePage(driver);

        String actualText = homePage.getWelcomeUsernameText(); // "Welcome MadhuV"
        String expectedText = "Welcome MadhuV"; 

        System.out.println("Expected: " + expectedText);
        System.out.println("Actual: " + actualText);

        Assert.assertEquals(actualText.trim(), expectedText.trim(), "Mismatch in welcome username!");
        test.pass("Welcome username is correctly displayed as: " + actualText);
    }

    @Test(priority = 2)
    public void testHomePageElements() throws InterruptedException {
    	
    	test = extent.createTest("Verify Home Page Elements");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWithPropertyCredentials();

        waitForSeconds(10);
        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(homePage.isLogoDisplayed(), "Logo is not displayed");
        test.pass("Logo is displayed");

        Assert.assertTrue(homePage.menuItemPresent(), "Menu items are not displayed");
        homePage.printAllMenuItems();
        test.pass("Menu items are present");

        Assert.assertTrue(homePage.categoriesPresent(), "Categories are not displayed");
        homePage.printAllCategories();
        test.pass("Categories are present");
    }
    
    @Test(priority = 3)
    public void testWelcomeTextMismatch() {
    	test = extent.createTest("Verify Home Page Elements");
        LoginPage loginPage = new LoginPage(driver);
    	loginPage.loginWithInvalidCredentials();

    /*	Alert alert = waitForElementVisibleEle.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "Wrong password.", "Unexpected alert message!");
        alert.accept();
        */
        HomePage homePage = new HomePage(driver);
        String actualText = homePage.getWelcomeUsernameText();
        Assert.assertNotEquals(actualText, "User does not exist.", "Unexpected welcome message found.");
        test.pass("Welcome text mismatch confirmed for invalid login.");
    }
	
    @Test(priority = 4)
    public void printAllHomePageTexts() {
        HomePage homePage = new HomePage(driver);
        List<String> texts = homePage.getAllVisibleTexts();
        for (String text : texts) {
            System.out.println("Text Found: " + text);
        }
        test.pass("All visible homepage texts printed to console.");
    }
}
