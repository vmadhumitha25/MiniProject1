package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContactEditPage extends BasePage {

	public ContactEditPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(className = ".contactTableBodyRow")
    WebElement firstContact;
	
	@FindBy(xpath = "//table[@id='contactTable']//tr")
	List<WebElement> contactRows;
	
	@FindBy(id = "edit-contact")
	WebElement editContactBtn;

	@FindBy(id = "firstName")
	WebElement Fname;
	
	@FindBy(id = "lastName")
	WebElement Lname;
	
	@FindBy(id = "email") 
    WebElement emailId;

    @FindBy(id = "phone") 
    WebElement phoneField;

    @FindBy(id = "birthdate") 
    WebElement birthdateField;

    @FindBy(css = "#submit") 
    WebElement saveButton;

    @FindBy(id = "error") 
    WebElement errorMessage;
    
    
    public void openContactToEdit() {
        firstContact.click();
    }
    
    public void clickEditContact() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(editContactBtn));

        editContactBtn.click();
    }
    
    public void clickEditForContact(String name) {
        List<WebElement> rows = driver.findElements(By.cssSelector(".contactTable tbody tr"));
        for (WebElement row : rows) {
            if (row.getText().contains(name)) {
                WebElement editButton = row.findElement(By.cssSelector(".edit-btn"));
                editButton.click();
                return;
            }
        }
        throw new RuntimeException("Contact with name '" + name + "' not found for edit.");
    }

    public void clickContact() {
    	//WebElement click = driver.findElement(By.cssSelector("tr:nth-child(3)" +contactName));
    	((WebElement) contactRows).click();
    }
    
    public void editContact(String first, String last, String dob, String email, String phone) {
    	clickEditContact();
        clearAndType(Fname, first);
        clearAndType(Lname, last);
        clearAndType(birthdateField, dob);
        clearAndType(emailId, email);
        clearAndType(phoneField, phone);
        saveButton.click();
    }

    public void submitEditedContact() {
    	saveButton.click();
    }
    private void clearAndType(WebElement field, String value) {
        field.clear();
        field.sendKeys(value);
    }
    
    public String getErrorMessage() {
        return errorMessage.getText();
    }
    
    public boolean isEditSuccessful() {
        try {
            return !driver.getCurrentUrl().contains("https://thinking-tester-contact-list.herokuapp.com/editContact");
        } catch (Exception e) {
            return false;
        }
    }
}
