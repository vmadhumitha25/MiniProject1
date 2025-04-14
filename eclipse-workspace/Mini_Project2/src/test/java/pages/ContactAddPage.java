package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactAddPage {

	WebDriver driver;

	@FindBy(id = "add-contact")
	WebElement addContactBtn;

	@FindBy(id = "firstName")
	WebElement firstNameField;

	@FindBy(id = "lastName")
	WebElement lastNameField;

	@FindBy(id = "birthdate")
	WebElement dateOfBirthField;

	@FindBy(css = "#email")
	WebElement emailIDField;

	@FindBy(xpath = "//input[@id='phone']")
	WebElement phoneNoField;

	@FindBy(css = "#street1")
	WebElement streetAddress1Field;

	@FindBy(id = "street2")
	WebElement streetAddress2Field;

	@FindBy(id = "city")
	WebElement cityField;

	@FindBy(css = "#stateProvince")
	WebElement stateField;

	@FindBy(id = "postalCode")
	WebElement postalCodeField;

	@FindBy(id = "country")
	WebElement countryField;

	@FindBy(xpath = "//button[@id='submit']")
	WebElement submitBtn;

	@FindBy(id = "error")
	WebElement errorMessage;

	@FindBy(xpath = "//div[contains(@class, 'alert-success')]")
	WebElement successMessage;

	@FindBy(xpath = "//table[@id='contactTable']//tr")
	List<WebElement> contactRows;

	public ContactAddPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clicklAddContactBtn() {
		addContactBtn.click();
	}

	public void enterFirstName(String firstname) {
		firstNameField.clear();
		firstNameField.sendKeys(firstname);
	}

	public void enterLastName(String lastname) {
		lastNameField.clear();
		lastNameField.sendKeys(lastname);
	}

	public void enterdateofbirth(String dob) {
		dateOfBirthField.clear();
		dateOfBirthField.sendKeys(dob);
	}

	public void enterEmail(String emailid) {
		emailIDField.clear();
		emailIDField.sendKeys(emailid);
	}

	public void enterPhoneNo(String phoneno) {
		phoneNoField.clear();
		phoneNoField.sendKeys(phoneno);
	}

	public void enterAddress1(String SAddress1) {
		streetAddress1Field.clear();
		streetAddress1Field.sendKeys(SAddress1);
	}

	public void enterAddress2(String SAddress2) {
		streetAddress2Field.clear();
		streetAddress2Field.sendKeys(SAddress2);
	}

	public void enterCity(String city) {
		cityField.clear();
		cityField.sendKeys(city);
	}

	public void enterState(String state) {
		stateField.clear();
		stateField.sendKeys(state);
	}

	public void enterPcode(String code) {
		postalCodeField.clear();
		postalCodeField.sendKeys(code);
	}

	public void enterCountry(String country) {
		countryField.clear();
		countryField.sendKeys(country);
	}

	public void clickSubmit() {
		submitBtn.click();
	}

	public String getErrorMessage() {
		return errorMessage.getText();
	}

	public boolean isErrorDisplayed() {
        return errorMessage.getSize() != null;
    }
	
	public void addContact(String firstname, String lastname, String birthdate, String email, String phone,
			String street1, String street2, String city, String state, String postalCode, String country, String ExpectedResult) {
		enterFirstName(firstname);
		enterLastName(lastname);
		enterdateofbirth(birthdate);
		enterEmail(email);
		enterPhoneNo(phone);
		enterAddress1(street1);
		enterAddress2(street2);
		enterCity(city);
		enterState(state);
		enterPcode(postalCode);
		enterCountry(country);
		submitBtn.click();
	}

	public boolean contactAddedSuccessfully() {
		return successMessage.isDisplayed();
	}

	public boolean ContactListed (String firstname, String lastname, String birthdate, String email, String phone,
			String street1, String street2, String city, String state, String postalCode, String country) {
	        for (WebElement row : contactRows) {
	            String rowText = row.getText();
	            if (rowText.contains(firstname) && rowText.contains(lastname)) {
	                return true;
	            }
	        }
	        return false;
	}
	
}
