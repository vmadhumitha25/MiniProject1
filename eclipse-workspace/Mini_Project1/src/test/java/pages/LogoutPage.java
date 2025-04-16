package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogoutPage {

	WebDriver driver;
	
	By LogoutButton = By.id("logout2");
	By LoginButton = By.id("login2");
	By CartMenu = By.id("cartur");
	
	public LogoutPage (WebDriver driver) {
		this.driver= driver;
	}
	
	public boolean logoutBtnDisplayed() {
		return driver.findElement(LogoutButton).isDisplayed();
	}
	public void clickLogoutBtn() {
		driver.findElement(LogoutButton).click();
	}
	public boolean loginBtnDisplayed() {
		return driver.findElement(LoginButton).isDisplayed();
	}
	
	public void cartmenu() {
		driver.findElement(CartMenu).click();
	}
	
	public void waitForModalToDisappear(By modalLocator, int timeoutInSeconds) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(modalLocator));
	}
}
