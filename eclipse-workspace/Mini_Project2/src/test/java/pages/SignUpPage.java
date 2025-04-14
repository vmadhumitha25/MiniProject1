package pages;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage {

	WebDriver driver;
	
	@FindBy(id = "signup")
	WebElement signupButton;
	
	@FindBy(id = "firstName")
	WebElement firstnameField;
	
	@FindBy(id = "lastName")
	WebElement lastnameField;
	
	@FindBy(id = "email")
	WebElement emailField;
	
	@FindBy(id = "password")
	WebElement passwordField;
	
	@FindBy(id = "error")
	WebElement errorMessage;
	
	@FindBy(css = "#submit")
	WebElement submitButton;
	 
	@FindBy(xpath = "//div[@class='success-message']")
	WebElement successMessage;
	
	public SignUpPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//Fluent interface for chaining
	
	public SignUpPage clickSignupBtn() {
		signupButton.click();
		return this;
	}
	public SignUpPage enterFirstName(String fname) {
		firstnameField.sendKeys(fname);
		return this;
	}
	
	public SignUpPage enterLastName(String lname) {
		lastnameField.sendKeys(lname);
		return this;
	}
	
	public SignUpPage enterEmail(String email) {
		emailField.sendKeys(email);
		return this;
	}
	
	public SignUpPage enterPassword(String passwrd) {
		passwordField.sendKeys(passwrd);
		return this;
	}
	public void submitSignUpButton() {
		submitButton.click();
		//return this;
	}
	
	public boolean errorMessageDisplayed() {
		return errorMessage.isDisplayed();
	}
	
	public String getErrorMessage() {
        return errorMessage.getText();
    }
	
	public boolean isAlertPresent() {
		Alert alert = driver.switchTo().alert();
		return true;

	}

	public boolean isContactListPageLoaded() {
	    return driver.getCurrentUrl().contains("contactList");
	}
	
	public String getAlertMessage() {
        try {
            Alert alert = driver.switchTo().alert();
            String msg = alert.getText();
            alert.accept();
            return msg;
        } catch (Exception e) {
            return "";
        }
    }
	
	public boolean isSignupSuccessful() {
	    new WebDriverWait(driver, Duration.ofSeconds(5))
		    .until(ExpectedConditions.urlContains("/contactList"));
		return true;
	}
	  
	/*  public void signup(String firstName, String lastName, String email, String password) {
		  SignUpPage signUp = new SignUpPage(driver);
		   signUp.clickSignupBtn();
		   signUp.enterFirstName(firstName);
		   signUp.enterLastName(lastName);
		   signUp.enterEmail(email);
		   signUp.enterPassword(password);
		   signUp.submitSignUpButton();
	        
	        // Validate signup success or failure
	        signUp.verifySignupSuccess();
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        wait.until(ExpectedConditions.or(
	        	    ExpectedConditions.alertIsPresent(),
	        	    ExpectedConditions.visibilityOfElementLocated(By.id("error-message")), // adjust this locator
	        	    ExpectedConditions.urlContains("contactList")
	        	));
	    }
	  */
	  public void logout() {
		    WebElement logoutButton = driver.findElement(By.xpath("//button[@id='logout']"));
		    logoutButton.click();
		    
		    // Wait until redirected to the login/signup page
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Login']")));  
		}
	public String getAlertText() {
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
	}
}
