package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

	WebDriver driver;
	By productTitle = By.cssSelector(".card-title a");
	By productName = By.cssSelector(".name");
	By productPrice = By.cssSelector(".price-container");
	By productDescri = By.id("more-information");
	By addToCart = By.className(".btn-success");
	By addToCartBtn = By.xpath("//a[contains(text(),'Add to cart')]");
	By productImage = By.cssSelector(".item img");
	
	public ProductPage(WebDriver driver) {
	    this.driver = driver;
	    PageFactory.initElements(driver, this);
	}

	// Method to click on the first product title
    public void clickProductTitle() {
       // WebElement product = driver.findElement(productTitle);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement product = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".card-title a")));
        product.click();
    }
	
	public String getProductName() {
		return driver.findElement(productName).getText();
	}
	public String getProductPrice() {
		return driver.findElement(productPrice).getText();
	}
	public String getProductDescription() {
		return driver.findElement(productDescri).getText();
	}
	
	public boolean isImageDisplayed() {
        return driver.findElement(productImage).isDisplayed();
    }
	public boolean isAddToCartDisplayed() {
		return driver.findElement(addToCart).isDisplayed();
	}
	
	 public String getAlertText() {
	        return driver.switchTo().alert().getText();
	    }
	 public void acceptAlert() {
		    driver.switchTo().alert().accept();
		}
	 

	    // Category click (like Phones, Laptops, Monitors)
	    public void clickCategory(String categoryName) {
	        By categoryLocator = By.xpath("//a[text()='" + categoryName + "']");
	        driver.findElement(categoryLocator).click();
	    }

	    // Select product by its visible name
	    public void selectProductByName(String productName) {
	        List<WebElement> products = driver.findElements(By.cssSelector(".card-title a"));
	        for (WebElement product : products) {
	            if (product.getText().equalsIgnoreCase(productName)) {
	                product.click();
	                break;
	            }
	        }
	    }
	 // Click the "Add to Cart" button
	public void clickAddToCart() {
	driver.findElement(addToCartBtn).click();
	}
}