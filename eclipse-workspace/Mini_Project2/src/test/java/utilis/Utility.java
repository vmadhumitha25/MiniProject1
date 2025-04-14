package utilis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

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

	public static String[][] readExcel(String sheetname) throws Exception {
		String filePath = "C:\\Users\\madhu\\eclipse-workspace\\Mini_Project2\\src\\test\\resources\\MiniProject2.xlsx";

		FileInputStream file = new FileInputStream(filePath); 
		XSSFWorkbook book = new XSSFWorkbook(file) ;

			XSSFSheet sheet = book.getSheet(sheetname);
			int rowCount = sheet.getLastRowNum();
			int columnCount = sheet.getRow(0).getLastCellNum();

			// Count only non-empty rows
		    int validRowCount = 0;
		    for (int i = 1; i <= rowCount; i++) {
		        if (sheet.getRow(i) != null) {
		            validRowCount++;
		        }
		    }

			String[][] data = new String[validRowCount][columnCount];
			DataFormatter formatter = new DataFormatter();
			int dataIndex = 0;
			
			for (int i = 1; i <= rowCount; i++) {
				XSSFRow row = sheet.getRow(i);
				if (row == null) continue; 
					for (int j = 0; j < columnCount; j++) {
						XSSFCell cell = row.getCell(j);
						data[i - 1][j] = (cell != null) ? formatter.formatCellValue(cell) : "";
				}
					dataIndex++;
			}

			book.close();
			file.close();
			return data;

		} 

	private static Properties properties;
	// Load the config file
	static {
		try {
			FileInputStream file = new FileInputStream(
					"C:\\Users\\madhu\\eclipse-workspace\\Mini_Project2\\src\\test\\resources" + "\\config.properties");
			properties = new Properties(); // Creating object
			properties.load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Failed to Load the config properties file" + e.getMessage());
		}
	}

//property key to fecth the value and return the correspoding property value
	public static String getProperty(String key) {
		if (properties == null) {
			System.out.println("Properties file not loaded!");
			return null;
		}
		return properties.getProperty(key);
	}

	public static void readFromPropFile(String filepath) throws IOException {

		FileReader file = new FileReader(filepath);
		prop = new Properties();
		prop.load(file);
	}

	public static String screenshot(String name) throws IOException {

		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = "C:\\Users\\madhu\\eclipse-workspace\\Mini_Project1\\screenshot" + name + " " + timestamp
				+ ".png";
		File dest = new File(path);
		FileUtils.copyFile(src, dest);
		return path;
	}

	public void waitForSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
