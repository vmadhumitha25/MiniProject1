package pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilis.Utility;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy (id = "email")
	WebElement email;
	
	By emailField = By.id("email");
	
	@FindBy (id = "password")
	WebElement Password;
	
	By passwordField = By.id("password");
	
	@FindBy (xpath = "//button[@id='submit']")
	WebElement submitBtn;
	
	By loginBtn = By.id("submit");
	
	@FindBy (css = "#logout")
	WebElement logoutBtn;
	
	@FindBy(id = "error")
	WebElement errorMessage;
	
	public void enterUsername(String enteremail) {
		email.clear();
		email.sendKeys(enteremail);
	}
	
	public void enterPassword(String password) {
		Password.clear();
		Password.sendKeys(password);
	}
	
	public void submitButton() {
		submitBtn.click();
	}
	
	public void logoutButton() {
		logoutBtn.click();
	}
	
	// Is logout button displayed
	public boolean isLogooutButtonDisplayed() {
		return logoutBtn.isDisplayed();
	}
	
	public String getAlertMessage() {
		Alert alert = driver.switchTo().alert();
		String text = alert.getText();
		alert.accept();
		return text;
	}

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }
    
    public String getErrorText() {
    	return errorMessage.getText();  
    }
    
	public void performLogin(String emailID, String password) {
		enterUsername(emailID);
		enterPassword(password);
		submitButton();
		//logoutButton();
	}
	
	public void loginFromProperties() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String email = Utility.getProperty("user.email");
	    String password = Utility.getProperty("user.password");
	    
	    System.out.println("Logging in with: " + email + " | " + password);
		/*driver.findElement(emailField).sendKeys(email);
	    driver.findElement(passwordField).sendKeys(password);
	    driver.findElement(loginBtn).click(); */
	    
	    wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
	    wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
	}
	
	 public boolean loginSuccessful() {
	        return driver.getCurrentUrl().contains("https://thinking-tester-contact-list.herokuapp.com/contactList"); 
	    }

}
