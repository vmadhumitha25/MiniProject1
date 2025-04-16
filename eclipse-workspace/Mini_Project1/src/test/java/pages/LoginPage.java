package pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseTest;
import utilis.Utility;

public class LoginPage extends BaseTest{

	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "login2")
	WebElement loginBtn;

	@FindBy(id = "loginusername")
	WebElement UserName;

	@FindBy(id = "loginpassword")
	WebElement Password;

	@FindBy(css = "button[onclick='logIn()']")
	WebElement submitBtn;

	@FindBy(id = "logout2")
	WebElement logoutBtn;

	@FindBy(id = "nameofuser")
	WebElement welcomeUser;

	By WelcomeUserText = By.id("nameofuser");

	// Is Login Button Visible
	public boolean isLoginButtonVisible() {
		return loginBtn.isDisplayed();
	}

	public void clickLoginButton() {
		loginBtn.click();
		// waitForSeconds(1);
	}

	public void enterUsername(String username) {
		UserName.clear();
		UserName.sendKeys(username);
	}

	public void enterPassword(String password) {
		Password.clear();
		Password.sendKeys(password);
	}

	public void submitLoginBtn()	{
		submitBtn.click();
	}
	
	public void submitLogin() {
		submitBtn.click();

		//Handle alert if appears
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		//System.out.println("Alert appeared: " + alert.getText());
		alert.accept();

		//System.out.println("No alert appeared after login.");
	}

	public void logoutButton() {
		logoutBtn.click();
	}

	public String getAlertMessage() {
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
	        String alertText = alert.getText();
	        alert.accept();  // Close the alert
	        return alertText;
	}
	
	public boolean isWelcomeMessageDisplayed() {
		return welcomeUser.isDisplayed();
	}

	public boolean isLoginModalVisible() {
		return UserName.isDisplayed() && Password.isDisplayed();
	}

	// Is logout button displayed
	public boolean isLogooutButtonDisplayed() {
		return logoutBtn.isDisplayed();
	}

	public void performLogin(String username, String password) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("logInModal")));
		clickLoginButton();
		enterUsername(username);
		enterPassword(password);
		submitLoginBtn();
	}

	public void loginWithPropertyCredentials() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		String username = Utility.getProperty("login.valid.username");
		String password = Utility.getProperty("login.valid.password");

		System.out.println("Logging in with: " + username + " | " + password);
		// loginFunction(username, password);
		clickLoginButton();
		waitForSeconds(5);
		waitForElementVisible(By.id("logInModalLabel"), 10);
		enterUsername(username);
		enterPassword(password);
		submitLoginBtn();
	}

	// Verify Welcome Message
	public String getWelcomeText() {
		return driver.findElement(WelcomeUserText).getText();
	}

	// Method to verify if expected username is present
	public boolean isCorrectUserLoggedIn(String expectedUsername) {
		try {
			String welcomeText = getWelcomeText(); // Example: "Welcome MadhuV"
			return welcomeText.contains(expectedUsername);
		} catch (Exception e) {
			return false;
		}
	}

	public void loginWithInvalidCredentials() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String invalidUsername = Utility.getProperty("login.invalid.username");
		String invalidPassword = Utility.getProperty("login.invalid.password");

		System.out.println("Attempting invalid login with: " + invalidUsername + " | " + invalidPassword);

		clickLoginButton();
		enterUsername(invalidUsername);
		enterPassword(invalidPassword);
		submitLoginBtn();
	}
}
