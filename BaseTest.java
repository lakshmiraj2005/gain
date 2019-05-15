package com.lexisnexis.risk.swqa.RIN.ui.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class BaseTest {
	public WebDriver driver;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	@BeforeSuite
    public void setup() {
  	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\asokma01\\chromedriver_win32\\chromedriver.exe");
  	  System.setProperty("webdriver.ie.driver","C:\\Users\\asokma01\\IEDriverServer_Win32_2.53.1\\IEDriverServer.exe");
    	System.setProperty("webdriver.gecko.driver","C:\\Users\\asokma01\\geckodriver-v0.24.0-win64\\geckodriver.exe");
    	
    	ExtentHtmlReporter reporter=new ExtentHtmlReporter("./Reports/Comp_report.html");
		
	    extent = new ExtentReports();
	    
	    extent.attachReporter(reporter);
	 
  }

	 @AfterMethod
		public void tearDown(ITestResult result) throws IOException
		{
			
			if(result.getStatus()==ITestResult.FAILURE)
			{
				String temp=Utility.getScreenshot(driver);
				
				test.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}
			
		
			//driver.quit();
			
		}
	
	 
	@AfterSuite
	public void tearDown(){
	    extent.flush();
	   }
}
