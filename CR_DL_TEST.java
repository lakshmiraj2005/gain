package com.lexisnexis.risk.swqa.RIN.ui.test;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.xpath.operations.And;
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
import com.lexisnexis.risk.swqa.RIN.ui.pages.DriverLicense;
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

public class CR_DL_TEST extends BaseTest {
	
		    
		  
		
    @Test(dataProvider="Authentication")
      public void comp_report_DL(String Browser ,String username, String password, String name, String ssn, String Header1, String Header2, String Header3, String Header4,String Header5,
    		  String Name,String LicenseNum, String IssueState,String IssueDate,String Exp_Date,String Name1,String LexID,String PSSN,String LicenseAddress, String DOB, String Gender,
    		  String Ethnicity,String Height,String LicenseNum1,String LicenseType,String LicenseClass,String IssuingState,String IssuingDate,String ExpirationDate,String DataSource) throws Throwable {
    	
    	 
	    test=extent.createTest("Comp Report Driver's License");
    	
    	login obj1 = PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.login.class);
        Dashboard obj2 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Dashboard.class);
        Search obj3 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Search.class);
        CompReport obj4=PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.CompReport.class);
        DriverLicense obj5= PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.DriverLicense.class);
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
    	driver.get("https://webdev02.risk.regn.net:506/#/auth/login");
        //driver.get("https://rincert.risk.regn.net/#/auth/login");
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
		
		
		obj4.DriverLicense.click();
	    obj4.Requestreport.click();
	    obj5 =new DriverLicense(driver);
	    
	    Boolean container =obj5.Container.isDisplayed();
		System.out.println("Driver License Container present is " +container);
		Assert.assertTrue(container);
	
		  test.log(Status.PASS, "Verified Driver License container present");
		Thread.sleep(10000);
		
		
		String DL =driver.findElement(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[1]/div[1]/span")).getText();
		System.out.println(DL);
		int s1=DL.length();
		System.out.println(s1);
		String num= DL.substring(17,s1-1);
		int DL_count=Integer.valueOf(num);
		System.out.println(DL_count);
		List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[2]/app-driver-license-widget/div/div[1]/app-collapsible-table-widget/div/table/tbody/tr"));
		//List<WebElement> rows =driver.findElements(By.cssSelector(".ng-tns-c121-33.ng-star-inserted"));
		int rowcount = rows.size()/2;
		System.out.println("ROW COUNT : "+rowcount);
		Assert.assertEquals(DL_count, rowcount);
		test.log(Status.PASS, "Verified number of rows with Driver's License count");
		
		
		
		String header1=obj5.header1.getText();
		String header2=obj5.header2.getText();
		String header3=obj5.header3.getText();
		String header4=obj5.header4.getText();
		String header5=obj5.header5.getText();
		
		Assert.assertEquals(header1,Header1);
		Assert.assertEquals(header2,Header2);
		Assert.assertEquals(header3,Header3);
		Assert.assertEquals(header4,Header4);
		Assert.assertEquals(header5,Header5);
	
		 test.log(Status.PASS, "All the Headers are verified");
		String dname=obj5.name.getText();
		String dLicenseNum=obj5.LicenseNum.getText();
		String dIssueState=obj5.IssueState.getText();
		String dIssueDate=obj5.IssueDate.getText();
		String dExpDate = obj5.ExpDate.getText();
		
		Assert.assertEquals(dname,Name);
		Assert.assertEquals(dLicenseNum,LicenseNum);
		Assert.assertEquals(dIssueState,IssueState);
		Assert.assertEquals(dIssueDate, IssueDate);
		Assert.assertEquals(dExpDate,Exp_Date);
		obj5.FirstRow.click();
		Boolean PI =obj5.PersonalInformation.isDisplayed();
		Assert.assertTrue(PI);
		Boolean LI= obj5.LicenseInfo.isDisplayed();
		Assert.assertTrue(LI);
		System.out.println("PI & LI is present");
		
		 String dName1=obj5.Name1.getText();
		 System.out.println("name is displayed as "+dName1);
		String dlexid=obj5.LexID.getText();
		String dpssn=obj5.pssn.getText();
		String dLA=obj5.LA.getText();
		String ddob=obj5.DOB.getText();
		String dGender=obj5.Gender.getText();
		String dEthnicity=obj5.Ethnicity.getText();
		String dHeight=obj5.Height.getText();
		
							
		Assert.assertEquals(dName1,Name1);
		Assert.assertEquals(dlexid,LexID);
		Assert.assertEquals(dpssn,PSSN);
		
  // Assert.uals(dLA, LicenseAddress);
		Assert.assertEquals(ddob,DOB);
		Assert.assertEquals(dGender,Gender);
		Assert.assertEquals(dEthnicity, Ethnicity);
		Assert.assertEquals(dHeight, Height);
		
		System.out.println("Personal Information is verified ");
		 test.log(Status.PASS, "Personal Information is verified");
		
		String dLnum= obj5.LNum.getText();
		String dLtype= obj5.LType.getText();
		String dLclass = obj5.LClass.getText();
		String dIS = obj5.Istate.getText();
		String dID=obj5.IDate.getText();
		String dExpDate1 = obj5.EXPDate.getText();
		String dDS = obj5.DS.getText();
		
		
		Assert.assertEquals(dLnum, LicenseNum1);
		Assert.assertEquals(dLtype, LicenseType);
		Assert.assertEquals(dLclass, LicenseClass);
		//Assert.assertEquals(dIS,IssuingState);
		Assert.assertEquals(dID,IssuingDate);
		Assert.assertEquals(dExpDate1,ExpirationDate);
		Assert.assertEquals(dDS,DataSource);
		test.log(Status.PASS, "License Information Section is Verified");
		
		System.out.println("Driver License section is verified");
		
		
		 
		  //Verify + & - Icon
		
			Boolean minus= obj5.minus.isDisplayed();
		System.out.println(minus);
		Assert.assertTrue(minus);
			
	obj5.minus.click();
		Boolean plus= obj5.plus.isDisplayed();
		System.out.println(plus);
		Assert.assertTrue(plus);
		 test.log(Status.PASS, "+ & - Icon is verified");
		  
		
		
		
		  test.log(Status.PASS, "Driver License  section is verified");
	 	   
		//logout 
		obj6= new logout(driver);
		obj6.logout();
		 test.log(Status.PASS, "Logged out");
	}
        
    
    
    
    @DataProvider
    public Object[][] Authentication() throws Exception{
    	 
        Object[][] testObjArray = ExcelUtils3.getTableArray("C:\\Users\\asokma01\\eclipse-workspace\\selenium-testng-RIN\\src\\test\\resources\\testData\\default.xlsx","DL");

        return (testObjArray);

}
  

 
 
 
}



 








 



