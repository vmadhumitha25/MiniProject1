package utilis;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

import base.BaseTest;

public class Listener extends BaseTest implements ITestListener {

	    public void TestSuccess(ITestResult result) throws IOException {
	    	test.log(Status.PASS, "Test Passed");
	    	String screenshotPath = null;
	    	//screenshotPath = screenshot(result.getMethod().getMethodName() + " Pass");
	    	test.addScreenCaptureFromPath(screenshotPath, "Passed Test Screenshot");
	    }
	    
	    public void TestFailure(ITestResult result) throws IOException {
	    	test.log(Status.FAIL, "Test Failed");
	    	String screenshotPath = null;
	    	//screenshotPath = screenshot(result.getMethod().getMethodName() + " Fail");
	    	test.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
	    }

}
