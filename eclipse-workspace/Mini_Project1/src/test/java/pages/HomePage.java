package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	WebDriver driver;

	By logo = By.id("nava");
	By welcomeUsername = By.id("nameofuser");
	By menuItems = By.cssSelector(".navbar-nav.ml-auto");
	By categories = By.cssSelector(".list-group-item");
	By categoryMenu = By.id("itemc");
	By categoryList = By.xpath("//a[@class='list-group-item']");
	By productTitle = By.className("card-title");
	// By HomePageBtn =By.xpath("//a[@class='nav-link'])[1]");
	@FindBy(xpath = "(//a[@class='nav-link'])[1]")
	WebElement HomeButton;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickHomePageBtn() {
		HomeButton.click();
	}

	public boolean isLogoDisplayed() {
		return driver.findElement(logo).isDisplayed();
	}

	public boolean isWelcomeUserdisplayed() {
		System.out.println("Welcome Username : " + welcomeUsername);
		return driver.findElement(welcomeUsername).isDisplayed();
	}

	public String getWelcomeUsernameText() {
		return driver.findElement(By.id("nameofuser")).getText(); // or use @FindBy if already set
	}

	// Menu Items
	public boolean menuItemPresent() {
		return driver.findElements(menuItems).size() > 0;
	}

	public void printAllMenuItems() {
		List<WebElement> items = driver.findElements(menuItems);
		System.out.println("Menu Items:");
		for (WebElement item : items) {
			System.out.println(" - " + item.getText());
		}
	}

	// Categories
	public boolean categoriesPresent() {
		return driver.findElement(categories).isDisplayed();
	}

	public void printAllCategories() {
		List<WebElement> cats = driver.findElements(categoryList);
		System.out.println("Categories:");
		for (WebElement cat : cats) {
			System.out.println(" - " + cat.getText());
		}
	}

	public void selectCategory(String categoryName) {
		List<WebElement> categories = driver.findElements(categoryMenu);
		for (WebElement category : categories) {
			if (category.getText().equalsIgnoreCase(categoryName)) {
				category.click();
				break;
			}
		}
	}

	public void selectProduct(String productName) {
		List<WebElement> products = driver.findElements(productTitle);
		for (WebElement product : products) {
			if (product.getText().equalsIgnoreCase(productName)) {
				product.click();
				break;
			}
		}
	}

	public List<String> getAllVisibleTexts() {
		List<String> visibleTexts = new ArrayList<>();
		List<WebElement> allElements = driver.findElements(By.xpath("//*[not(self::script or self::style)]"));

		for (WebElement element : allElements) {
			String text = element.getText().trim();
			if (!text.isEmpty()) {
				visibleTexts.add(text);
				// return visibleTexts;
			}
		}
		return visibleTexts;
	}

	public boolean isHomePageDisplayed() {
		String welcomeText = driver.findElement(welcomeUsername).getText();
		return welcomeText.contains("Welcome");

	}
}
