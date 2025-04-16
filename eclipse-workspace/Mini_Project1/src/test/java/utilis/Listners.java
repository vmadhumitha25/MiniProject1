package utilis;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import base.BaseTest;

public class Listners extends BaseTest implements ITestListener{

	Utility util;
	/*public Listners( ) {
		super();
	}*/

    @Override
    public void onTestStart(ITestResult result) {
        
        test = extent.createTest(result.getMethod().getMethodName());
    }

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		test.log(Status.PASS, "Test pased");
		
		String screenShotpath=null;
		try {
			screenShotpath = screenshot(result.getMethod().getMethodName()+" pass");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test.addScreenCaptureFromPath(screenShotpath, "Passed Test Screenshot");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.fail(result.getThrowable());
	    String screenShotpath=null;
	    //System.out.println("Driver in listener: " + (driver != null ? "Not Null" : "Null"));
		try {
			screenShotpath = screenshot(result.getMethod().getMethodName()+ " fail");
			test.addScreenCaptureFromPath(screenShotpath, "Failed Test Screenshot");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 @Override
	    public void onTestSkipped(ITestResult result) {
	        // Log skipped test
	        test.log(Status.SKIP, "Test skipped");
	    }
		
}
