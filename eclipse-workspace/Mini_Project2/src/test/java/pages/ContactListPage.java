package pages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BaseTest;

public class ContactListPage {

	WebDriver driver;

	public ContactListPage (WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".contactTableHead")
	List<WebElement> contactList;

	@FindBy(css = ".contactTableBodyRow")
	public
	List<WebElement> contactRows;

	@FindBy(id = "th:nth-child(1)")
	List<WebElement> emailColumn;

	@FindBy(xpath = "//th[normalize-space()='Phone']")
	WebElement phoneNoColumn;

	// Names column
	@FindBy(css = "th:nth-child(1)")
	List<WebElement> nameColumn;

	// Phone numbers column
	@FindBy(xpath = "(//th[normalize-space()='Phone'])[1]")
	List<WebElement> phoneNumberColumn;

	public void printAllContacts() {
	//	System.out.println("Total Contact Rows Found: " + contactRows.size());
		for (WebElement row : contactRows) {
			System.out.println("Contact Info: " + row.getText());
		}
	}

	public boolean contactDisplayed(String ContactName) {
		for (WebElement contact : contactRows) {
			if (contact.getText().contains(ContactName)) {
				return true;
			}
		}
		return false;
	}

	public String contactInfoByName(String name) {
		for (WebElement row : contactRows) {
			if (row.getText().contains(name)) {
				return row.getText();
			}
		}
		return "Contact not found";
	}

	public boolean sortedByName() {
		List<String> actualOrder = new ArrayList<>();
		for (WebElement ln : nameColumn) {
			actualOrder.add(ln.getText().toLowerCase());
		}

		List<String> sortedOrder = new ArrayList<>(actualOrder);
		Collections.sort(sortedOrder);
		return actualOrder.equals(sortedOrder);
	}

	public boolean areAllEmailsValid() {
		String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		for (WebElement email : emailColumn) {
			if (!email.getText().matches(regex)) {
				return false;
			}
		}
		return true;
	}

	public boolean isContactDisplayed(String firstname, String lastname) {
        List<WebElement> contacts = driver.findElements(By.cssSelector(".contactTableBodyRow"));

        for (WebElement contact : contacts) {
            String fullName = contact.findElement(By.cssSelector("td:nth-child(1)")).getText();
            if (fullName.equalsIgnoreCase(firstname + " " + lastname)) {
                return true;
            }
        }
        return false;
    }

	
	public void takeContactListScreenshot(String filename) throws IOException {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File dest = new File("screenshots/" + filename + ".png");
		FileUtils.copyFile(src, dest);
	}

	public void openFirstContact() {
		if (!contactRows.isEmpty()) {
	}
        contactRows.get(0).click();
    }
}
