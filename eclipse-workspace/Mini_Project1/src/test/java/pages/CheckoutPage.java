package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class CheckoutPage {

	WebDriver driver;
	WebDriverWait wait;

	By checkoutModal = By.id("orderModal");
	By totalPrice = By.id("totalm");

	@FindBy(xpath = "//button[text()='Place Order']")
	WebElement placeOrderButton;

	@FindBy(id = "name")
	WebElement nameField;

	@FindBy(id = "country")
	WebElement countryField;

	@FindBy(id = "city")
	WebElement cityField;

	@FindBy(id = "card")
	WebElement cardField;

	@FindBy(id = "month")
	WebElement monthField;

	@FindBy(id = "year")
	WebElement yearField;

	@FindBy(xpath = "//button[text()='Purchase']")
	WebElement purchaseButton;

	@FindBy(xpath = "//p[@class='lead text-muted ']")
	WebElement confirmationText;
	
	By confirmation = By.className("sweet-alert"); // Success popup

	@FindBy(xpath = "//button[text()='Purchase']")
	WebElement purchaseBtn;

	@FindBy(xpath = "//h2[contains(text(),'Thank you for your purchase!')]")
	WebElement purchaseSuccessMessage;

	@FindBy(xpath = "//div[@class='sweet-alert  showSweetAlert visible']//p")
	WebElement purchaseDetails;

	@FindBy(xpath = "//button[text()='OK']")
	WebElement okButton;
	
	By confirmMessage = By.xpath("//h2[normalize-space()='Thank you for your purchase!']");

	// By errorMessage = By.xpath("//div[contains(@class,'alert-danger')]");

	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void PlaceOrderBtn() {

		Assert.assertNotNull(placeOrderButton, "Place Order button not initialized!");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("orderModal")));
	    wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click();
	}
	public boolean CheckoutModalDisplayed() {
		return driver.findElement(checkoutModal).isDisplayed();
	}

	public boolean checkoutFormDisplayed() {
		return nameField.isDisplayed() && countryField.isDisplayed() && cityField.isDisplayed()
				&& cardField.isDisplayed() && monthField.isDisplayed() && yearField.isDisplayed();
	}
	
	public Map<String, Boolean> areCheckoutFieldsPresent() {
	    Map<String, Boolean> fieldPresence = new HashMap<>();

	    fieldPresence.put("Name", nameField.isDisplayed());
	    fieldPresence.put("Country", countryField.isDisplayed());
	    fieldPresence.put("City", cityField.isDisplayed());
	    fieldPresence.put("Card", cardField.isDisplayed());
	    fieldPresence.put("Month", monthField.isDisplayed());
	    fieldPresence.put("Year", yearField.isDisplayed());

	    return fieldPresence;
	}	

	public List<String> getCheckoutFieldPlaceholders() {
	    List<String> placeholders = new ArrayList<>();
	    placeholders.add(nameField.getAttribute("placeholder"));
	    placeholders.add(countryField.getAttribute("placeholder"));
	    placeholders.add(cityField.getAttribute("placeholder"));
	    placeholders.add(cardField.getAttribute("placeholder"));
	    placeholders.add(monthField.getAttribute("placeholder"));
	    placeholders.add(yearField.getAttribute("placeholder"));
	    return placeholders;
	}
	
	public void fillCheckoutDetails(String name, String country, String city, String creditCard, String month,
			String year) {
		//PlaceOrderBtn();
		nameField.sendKeys(name);
		countryField.sendKeys(country);
		cityField.sendKeys(city);
		cardField.sendKeys(creditCard);
		monthField.sendKeys(month);
		yearField.sendKeys(year);
	}

	public void clickPurchase() {
		purchaseButton.click();
	}

	public boolean purchaseSuccessful() {
		return driver.findElements(confirmMessage).size() > 0;
	}

	public boolean isPurchaseSuccessful() {
	    //waitForSeconds(2);
	    return purchaseSuccessMessage.isDisplayed();
	}

	public String getPurchaseDetails() {
	    return purchaseDetails.getText();
	}

	public void confirmOK() {
	    okButton.click();
	}
	public boolean isConfirmationDisplayed() {
		 return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='OK']"))).isDisplayed();
    }

    public void closeConfirmation() {
        okButton.click();
    }

    public String getAlertMessageIfAny() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();  // Close the alert
        return alertText;
}

}
