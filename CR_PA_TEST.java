package com.lexisnexis.risk.swqa.RIN.ui.test;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.support.PageFactory;
import com.lexisnexis.risk.swqa.RIN.ui.pages.CompReport;
import com.lexisnexis.risk.swqa.RIN.ui.pages.Dashboard;
import com.lexisnexis.risk.swqa.RIN.ui.pages.PossibleAssociates;
import com.lexisnexis.risk.swqa.RIN.ui.pages.Search;
import com.lexisnexis.risk.swqa.RIN.ui.pages.launch;
import com.lexisnexis.risk.swqa.RIN.ui.pages.login;

import com.lexisnexis.risk.swqa.RIN.ui.pages.logout;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
//import com.sun.jna.platform.FileUtils;

public class CR_PA_TEST extends BaseTest {
	
       
		 
	 
		
    @Test(dataProvider="Authentication")
      public void Comp_report_PA(String Browser ,String username, String password, String name, String ssn, String Header1, String Header2, String Header3, String Header4,
    		  String Name,String LexID,String SSN, String DOB, String Age, String Address, String Dates, String Phone) throws Throwable {
    	
    	 test=extent.createTest("Comp Report Possible Associates");
    	
    	login obj1 = PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.login.class);
        Dashboard obj2 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Dashboard.class);
        Search obj3 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Search.class);
        CompReport obj4=PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.CompReport.class);
        PossibleAssociates obj5= PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.PossibleAssociates.class);
        logout obj6= PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.logout.class);
        
        
    	
    	
    	if (Browser.equalsIgnoreCase("Chrome")) {
    	driver = new ChromeDriver();
    	  	}
    	else if(Browser.equalsIgnoreCase("IE")){
    		 driver=new InternetExplorerDriver();
    	}
    	else if(Browser.equalsIgnoreCase("Firefox")) {
    		driver = new FirefoxDriver();
    	}
    	driver.manage().window().maximize();
    	//driver.get("https://webdev02.risk.regn.net:506/#/auth/login");
        driver.get("https://rincert.risk.regn.net/#/auth/login");
       // Thread.sleep(300);
        
        //login
        obj1= new login(driver);    	   
    	obj1.login(username,password);
        Thread.sleep(3000);
        
        
       //Verify username is displayed 
    	obj2=new Dashboard(driver);
        String dispalyedname =obj2.name.getText();
        Assert.assertEquals(dispalyedname,name);
        test.log(Status.PASS, "Logged in to RIN Successfully");
 	   
	    
        
        
        //Search with ssn
    	obj3= new Search(driver);
	
		
		obj3.searchlink.click(); 
		Thread.sleep(3000);
	
		obj3.ssn.sendKeys(ssn);
			
		obj3.adv_search.click();
		Thread.sleep(10000);
		
		obj3.identitytab.click();

		obj3.Scorecard.click();
		Thread.sleep(3000);
		String Displayed_ssn = obj3.scorecard_ssn.getText();
	    
	    Assert.assertEquals(ssn,Displayed_ssn);
	    test.log(Status.PASS, "searched with ssn");
        
	    //select CompReport & select possible Associates 
		obj4= new CompReport(driver);
		obj4.Compreport.click();
		test.log(Status.PASS, "Comp Report is selected");
	Boolean selectall = obj4.Selectall.isSelected();
	System.out.println(selectall);
		if(obj4.Selectall.isSelected()) {
			Thread.sleep(3000);	
		
	        obj4.Selectall.click();
			System.out.println("checkbox deselected");
		}
	else{
		obj4.Selectall.click();
		Thread.sleep(3000);
		obj4.Selectall.click();
		
	}
		
		
		obj4.associates.click();
	    obj4.Requestreport.click();
	    obj5 =new PossibleAssociates(driver);
	    
	    Boolean container =obj5.Container.isDisplayed();
		System.out.println("Possible Associates Container present is " +container);
		Assert.assertTrue(container);
		  test.log(Status.PASS, "Verified Possible Assocaites container present");
		Thread.sleep(10000);
		
		String PA =driver.findElement(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[1]/div[1]/span")).getText();
		System.out.println(PA);
		int s1=PA.length();
		System.out.println(s1);
		String num= PA.substring(21,s1-1);
		int PA_count=Integer.valueOf(num);
		System.out.println(PA_count);
		List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[2]/app-possible-associates-widget/div/div[1]/mat-table/mat-row"));
		int rowcount = rows.size();
		System.out.println("ROW COUNT : "+rowcount);
		Assert.assertEquals(PA_count, rowcount);
		test.log(Status.PASS, "Verified number of rows with Possible Assocaites count");
		
		String header1=obj5.header1.getText();
		String header2=obj5.header2.getText();
		String header3=obj5.header3.getText();
		String header4=obj5.header4.getText();
		System.out.println(header1  + header2  + header3 + header4);
		
		String dname=obj5.name.getText();
		String dlexid=obj5.LexID.getText();
		String dssn=obj5.SSN.getText();
		String ddob=obj5.DOB.getText();
		String dage=obj5.Age.getText();
		
		String daddress=obj5.Address.getText();
		String ddates=obj5.Dates.getText();
		String dphone=obj5.Phone.getText();
		
		Assert.assertEquals(header1,Header1);
		Assert.assertEquals(header2,Header2);
		Assert.assertEquals(header3,Header3);
		Assert.assertEquals(header4,Header4);
		Assert.assertEquals(dname,Name);
		Assert.assertEquals(dlexid,LexID);
		Assert.assertEquals(dssn,SSN);
		Assert.assertEquals(ddob,DOB);
		Assert.assertEquals(dage,Age);
		Assert.assertEquals(daddress,Address);
		Assert.assertEquals(ddates,Dates);
		Assert.assertEquals(dphone,Phone);
		System.out.println("Possible Associates section is verified");
		
		  test.log(Status.PASS, "Possible Associates section is verified");
		  
		  
		  
		  //Verify + & - Icon
		
			Boolean minus= obj5.minusIcon.isDisplayed();
		System.out.println(minus);
		Assert.assertTrue(minus);
			
	obj5.minusIcon.click();
		Boolean plus= obj5.plusIcon.isDisplayed();
		System.out.println(plus);
		Assert.assertTrue(plus);
		 test.log(Status.PASS, "+ & - Icon is verified");
		  
	   
		//logout 
		obj6= new logout(driver);
		obj6.logout();
		 test.log(Status.PASS, "Logged out");
		 
	}
        
    
    
    
    @DataProvider
    public Object[][] Authentication() throws Exception{
    	 
        Object[][] testObjArray = ExcelUtils3.getTableArray("C:\\Users\\asokma01\\eclipse-workspace\\selenium-testng-RIN\\src\\test\\resources\\testData\\default.xlsx","PA");

        return (testObjArray);

}
  
   

}
 








 



