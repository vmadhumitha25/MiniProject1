package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContactDeletePage extends BasePage {

	// WebDriver driver;

	public ContactDeletePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	WebDriverWait wait;

	@FindBy(id = "delete")
	WebElement deleteButton;

	By deleteBtn = By.xpath("//button[@id='delete']");
	
	@FindBy(className = ".contactTableBodyRow")
	List<WebElement> firstContact;

	By contactCards = By.className("contactTableRow");
	
	@FindBy(css = ".contactTable tbody tr")
	List<WebElement> contactRows;
	
	 By contactCardBy = By.className("contactTableRow");

	@FindBy(xpath = "//table[@id='contactTable']//tr")
	List<WebElement> ListcontactRows;

	public void deleteContact() {
		deleteButton.click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		alert.accept();
	}

	public int getContactCount() {
		return firstContact.size();
	}

	/*
	 * public void openContactToDelete() { firstContact.click(); }
	 */
	// Find a contact row by name
	public WebElement getRowByName(String name) {
		for (WebElement row : contactRows) {
			if (row.getText().toLowerCase().contains(name.toLowerCase())) {
				return row;
			}
		}
		return null;
	}

	// Click delete for a specific contact by name
	public boolean deleteContactByEmail(String email) {
		try {
			for (WebElement row : contactRows) {
				if (row.getText().contains(email)) {
					//WebElement deleteButton = row.findElement(By.cssSelector("#delete"));
					System.out.println("Clicking delete for: " + email);
					deleteButton.click();

					// Handle confirmation popup
					wait.until(ExpectedConditions.alertIsPresent());
					Alert alert = driver.switchTo().alert();
					alert.accept();

					System.out.println("Contact deleted: " + email);
					return true;
				}
			}
			System.out.println("Contact not found: " + email);
			return false;
		} catch (Exception e) {
			System.out.println("Exception during deletion: " + e.getMessage());
			return false;
		}
	}

	// Cancel Delete Button
	public void cancelDeleteByName(String contactName) {
		List<WebElement> contacts = driver.findElements(contactCardBy);
        for (WebElement contact : contacts) {
            if (contact.getText().contains(contactName)) {
                //contact.findElement(deleteBtn).click();
                wait.until(ExpectedConditions.alertIsPresent());
                Alert alert = driver.switchTo().alert();
                alert.dismiss(); // Cancel delete
                System.out.println("Cancelled delete for: " + contactName);
                break;
            }
        }
	}

	// Verify if contact still exists
	public boolean contactDisplayed(String contactName) {
		for (WebElement row : contactRows) {
			 String rowText = row.getText().trim().toLowerCase();
			 if (rowText.contains(contactName.trim().toLowerCase())) {		          
				System.out.println("Contact found: " + contactName);
			}
		}
		System.out.println("Contact not found: " + contactName);
		return false;
	}
}
