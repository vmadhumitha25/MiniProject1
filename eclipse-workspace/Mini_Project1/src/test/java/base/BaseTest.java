package base;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
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
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.beust.jcommander.Parameter;
import com.github.dockerjava.transport.DockerHttpClient.Request.Method;
import com.google.protobuf.ByteString.Output;

import utilis.Listners;
import utilis.Utility;

public class BaseTest extends Utility{
	
	@BeforeSuite
	public void setupReport() {
		ExtentSparkReporter reporter = new ExtentSparkReporter("C:\\Users\\madhu\\eclipse-workspace\\Mini_Project1\\reports\\Mini-Project 1.html");
		reporter.config().setReportName("Mini Project 1 Report");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}

   @BeforeClass
	public void testDetails() {
		ExtentTest test = extent.createTest(testName,testDescription);
		test.assignCategory(testCategory);
		test.assignAuthor(testAuthor);
	}
    
	@BeforeMethod
	@Parameters("browser")
	public void launchingBrowserandLoadingURL(@Optional("chrome")String browser){
		if (browser == null) {
            browser = Utility.getProperty("browser"); // Read from config file
        }
		//select the browser based on parameter
		if(browser.equalsIgnoreCase("chrome")) {
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
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(Utility.getProperty("implicitWait"))));
		
	
	}
	
	//@AfterClass
	//Capture the screenshot if the test fails
	 /*public void captureResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = Listners.captureScreenshot(driver, result.getName());
            test.fail("Test Failed. Screenshot below: ").addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Passed Successfully");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Skipped");
        }
    }
*/
	public static void readFromPropFile(String filepath) throws IOException {

		FileReader file = new FileReader(filepath);
		prop = new Properties();
		prop.load(file);
	}
	
	 public void waitForElementVisibleEle(WebElement element, int timeoutSec) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
	        wait.until(ExpectedConditions.visibilityOf(element));
	    }
	
	public void waitForElementVisible(By locator, int timeoutInSeconds) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void waitForSeconds(int seconds) {
	    try {
	        Thread.sleep(seconds * 1000);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }
	}
	
	public boolean handleAlertIfPresent(int timeoutInSeconds) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		System.out.println("Alert found: " + alert.getText());
		alert.accept();
		return true;
	}
	
	@AfterMethod
	public void browserquit() {
		if(driver!=null)
		driver.quit();
		driver = null;
	}
	
	@DataProvider
	public String[][] readData() throws Exception {
		
		String[][] data = getDataExcel(sheetname);
		return data;
	}
	
	@AfterSuite
	public void reportClose() {
		extent.flush();  // mandatory to close the report
	}


}
