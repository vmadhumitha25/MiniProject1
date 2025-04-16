package pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage extends BasePage {

	public SignUpPage(WebDriver driver) {
		super(driver);
	}

	// WebDriver driver;

	@FindBy(id = "signin2")
	WebElement signupButton;

	@FindBy(id = "sign-username")
	WebElement usernameField;

	@FindBy(id = "sign-password")
	WebElement passwordField;

	@FindBy(css = "button[onclick='register()']")
	WebElement confirmsignUpButton;

	 @FindBy(id = "signInModalLabel")
	 WebElement signupModal;

	public boolean signupButtonVisible() {
		return signupButton.isDisplayed();
	}
	
	public void clickSignupButton() {
		signupButton.click();
	}

	public boolean signupModalDisplayed() {
		return signupModal.isDisplayed();
	}

	public void signUp(String username, String password) throws InterruptedException {
		usernameField.sendKeys(username);
		passwordField.sendKeys(password);
		confirmsignUpButton.click();
		Thread.sleep(500);
	}
	
	public void confirmSignUpButton() { 
		confirmsignUpButton.click();; 
	}
	
	public String getSignUpAlertMessage() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
	        String alertText = alert.getText();
	        alert.accept();  // dismiss the alert
	        return alertText;
	}
}
