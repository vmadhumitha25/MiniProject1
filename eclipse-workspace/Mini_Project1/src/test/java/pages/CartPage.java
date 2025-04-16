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

import base.BaseTest;

public class CartPage extends BaseTest{

	WebDriver driver;

	@FindBy(id = "cartur")
	WebElement cartButton;
	
	@FindBy(xpath = "//button[normalize-space()='Place Order']")
	WebElement placeOrderBtn; 
	
	@FindBy(linkText = "Delete")
	WebElement deleteItem;
	
	@FindBy(xpath = "//tr") //"//tbody/tr"
	List <WebElement> cartItems;
	
	@FindBy(xpath = "//tr/td[2]") // or some appropriate locator for cart rows
	List<WebElement> cartItemsCount;
	
	@FindBy(id ="totalp")
	WebElement totalPrice;
	
	public CartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//Navigate to Cart Page
	
	public void goToCart() {
		cartButton.click();
		for (int i = 0; i < 2; i++) {
	            WebElement cartElement = new WebDriverWait(driver, Duration.ofSeconds(5))
	                .until(ExpectedConditions.elementToBeClickable(placeOrderBtn));
	            cartElement.click();
	        }
	}
	public void goToCartButton() {
		cartButton.click();
	}
	
	public int getItemCount() {
		return cartItemsCount.size();
	}
	
	public void deletefirstItem() {
		deleteItem.click();
	}
	
	public int getTotalPrice() {
		waitForSeconds(2);
		WebElement totalElement = driver.findElement(By.id("totalp"));
	    String priceText = totalElement.getText().trim();
	    System.out.println("Total Price : " +priceText);
	    if (priceText.isEmpty()) {
            System.out.println("Total price is empty. Returning 0.");
            return 0;
        }
		return Integer.parseInt(totalPrice.getText());
		//return totalPrice.getSize();
	}
	
    public boolean isPlaceOrderEnabled() {
        return driver.findElement((By) placeOrderBtn).isEnabled();
    }
	
	public void placeOrder() {
		placeOrderBtn.click();
	}
	
	public void clearAllItems() throws InterruptedException {
		List<WebElement> deleteButtons = driver.findElements(By.linkText("Delete"));

		if (deleteButtons.size() > 0) {
		    deleteButtons.get(0).click();
		    System.out.println("Item deleted from cart.");
		} else {
		    System.out.println("Cart is already empty. No item to delete.");
		}
		
	    while (getItemCount() > 0) {
	        deletefirstItem();
	        Thread.sleep(1000);
	    }
	}
}
