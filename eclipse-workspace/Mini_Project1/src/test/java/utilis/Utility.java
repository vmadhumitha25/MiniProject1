package utilis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class Utility {

	public static WebDriver driver;
	public static Properties prop;
	public static String filepath;
	public String sheetname;
	public static ExtentReports extent;
	public static ExtentTest test;
	public String testName, testDescription, testCategory, testAuthor;

	public static String[][] getDataExcel(String sheetname) throws Exception {
		//FileInputStream fis = new FileInputStream(new File(filePath));
		XSSFWorkbook book = new XSSFWorkbook("C:\\Users\\madhu\\eclipse-workspace\\Mini_Project1\\src\\test\\resources\\Mini Project-1.xlsx");
		XSSFSheet sheet = book.getSheet(sheetname);
		int rowCount = sheet.getLastRowNum();
		System.out.println("Row count: " + rowCount);
		int columnCount = sheet.getRow(0).getLastCellNum();
		System.out.println("Column count: " + columnCount);
		
		String[][] data = new String[rowCount][columnCount];
		DataFormatter formatter = new DataFormatter();

		for (int i = 1; i <= rowCount; i++) {
			XSSFRow row = sheet.getRow(i);
			if (row == null) continue; 
			for (int j = 0; j < columnCount; j++) {
				XSSFCell cell = row.getCell(j);
				//System.out.println(cell.getStringCellValue());
				//data[i-1][j] = cell.getStringCellValue();
				//data[i - 1][j] = getCellAsString(cell);
				//data[i - 1][j] = formatter.formatCellValue(cell);
				data[i - 1][j] = formatter.formatCellValue(cell);
			}
		}
		book.close();
		return data;

	}

	public static Properties properties;
	// Load the config file
	static {
		try {
			FileInputStream file = new FileInputStream("src/test/resources/config.properties");
			properties = new Properties(); // Creating object
			properties.load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Failed to Load the config properties file" + e.getMessage());
		}
	}

	// property key to fecth the value and return the correspoding property value
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static String screenshot(String name) throws IOException {

		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = "C:\\Users\\madhu\\eclipse-workspace\\Mini_Project1\\screenshot" + name + " " +timestamp+".png";
		File dest = new File(path);
		FileUtils.copyFile(src, dest);
		return path;
	}
	
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
	
	public boolean handleIfAlertPresent(int timeoutSeconds) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
	        wait.until(ExpectedConditions.alertIsPresent());
	        Alert alert = driver.switchTo().alert();
	        System.out.println("Alert appeared: " + alert.getText());
	        alert.accept();
	        return true;
	}
	
	public void waitForSeconds(int seconds) {
	    try {
	        Thread.sleep(seconds * 1000);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }
	}
	

}
