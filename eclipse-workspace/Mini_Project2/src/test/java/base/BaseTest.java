package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.beust.jcommander.Parameter;
import com.google.protobuf.ByteString.Output;

import pages.LogoutPage;
import utilis.Listener;
import utilis.Utility;

@Listeners(utilis.Listener.class)

public class BaseTest extends Utility {

	@BeforeSuite
	public void setupReport() {
		ExtentSparkReporter reporter = new ExtentSparkReporter(
				"C:\\Users\\madhu\\eclipse-workspace\\Mini_Project2\\reports\\Mini-Project_2_ExtendReport.html");
		reporter.config().setReportName("Mini Project 2 Report");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}

	@BeforeClass
	public void testDetails() {
		ExtentTest test = extent.createTest(testName, testDescription);
		test.assignCategory(testCategory);
		test.assignAuthor(testAuthor);
	}

	@BeforeMethod
	@Parameters("browser")
	// public void launchingBrowserandLoadingURL(@Optional("chrome")String browser){
	public void initializeDriver() {
		String browser = Utility.getProperty("browser");
		if (browser == null) {
			browser = Utility.getProperty("browser"); // Read from config file
		}
		// select the browser based on parameter
		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else {
			driver = new ChromeDriver();
		}

		// Launch the Application URL from properties file
		driver.get(Utility.getProperty("baseURL"));

		driver.manage().window().maximize();

		// Set the Implicit Time
		driver.manage().timeouts()
				.implicitlyWait(Duration.ofSeconds(Integer.parseInt(Utility.getProperty("implicitWait"))));
	}

	/*
	 * @AfterMethod public void logoutIfLoggedIn() { try { LogoutPage logoutPage =
	 * new LogoutPage(driver); if (logoutPage.logoutBtnDisplayed()) {
	 * logoutPage.clickLogoutBtn(); } } catch (Exception e) {
	 * System.out.println("User already logged out."); } }
	 */
	public static void readFromPropFile(String filepath) throws IOException {
		prop = new Properties();
		FileInputStream file = new FileInputStream(filepath);
		prop.load(file);
		file.close();
	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

	public void waitForElementVisibleEle(WebElement element, int timeoutSec) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementVisible(By locator, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void WaitUntil(int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
	}

	@AfterMethod
	public void browserquit() {
		if (driver != null) {
			driver.quit();
			driver = null; // Reset the driver
		}
	}

	@AfterSuite
	public void reportClose() {
		extent.flush();
	}

}
