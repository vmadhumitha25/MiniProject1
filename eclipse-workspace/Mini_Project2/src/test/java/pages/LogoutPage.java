package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LogoutPage {

	WebDriver driver;
	
	By LogoutButton = By.cssSelector("#logout");
	By SignupButton = By.id("signup");
	
	public LogoutPage (WebDriver driver) {
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isloggedout() {
		return driver.findElement(SignupButton).isDisplayed();
	}
	public boolean isLogoutVisible() {
	        return driver.findElement(LogoutButton).isDisplayed();
	}
	
	public boolean logoutBtnDisplayed() {
		return driver.findElement(LogoutButton).isDisplayed();
	}
	public boolean isOnLoginPage() {
	    return driver.getCurrentUrl().contains("/login");
	}
	
	public void clickLogoutBtn()  {
		driver.findElement(LogoutButton).click();
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        boolean urlChanged = wait.until(ExpectedConditions.urlContains("https://thinking-tester-contact-list.herokuapp.com/"));
	        if (urlChanged) {
	            System.out.println("User successfully redirected to login page after logout.");
	        }else {
	        System.out.println("Timeout: User was not redirected to login page after logout.");
	        }
	}
	
}
