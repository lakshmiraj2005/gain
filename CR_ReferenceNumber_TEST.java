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

import com.lexisnexis.risk.swqa.RIN.ui.pages.PeopleAtWork;
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

public class CR_ReferenceNumber_TEST extends BaseTest {
	
	 
	 
		
    @Test(dataProvider="Authentication")
      public void Comp_report(String Browser,String username,String	password,String	name,String	LexID,String Ref,String RefNum,String B_RefNum1,String ssn,String	Header1,String	Header2,String	Header3,String	Header4,
    		 String Header5,String Header6,String	Name,String Title,String CompanyName,String	Address,String	PhoneNumber,String Dates,String	PI_Name,String	PI_NameVal,String	PI_Title,
    		 String	PI_TitleVal,String	PI_LexiD,String PI_LexIdVal,String	PI_SSN,String	PI_SSNVal,String	CI_Company,String	CI_CompanyVal,String CI_Address,String CI_AddressVal,String	CI_PhoneNumber,
    		 String	CI_PhoneNumberVal,String CI_FEIN,String CI_FEINVal,String CI_Date, String CI_DateVal) throws Throwable {
    	    	
    	 test=extent.createTest("Comp Report People at work with Reference Number");
    	login obj1 = PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.login.class);
        Dashboard obj2 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Dashboard.class);
        Search obj3 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Search.class);
        CompReport obj4=PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.CompReport.class);
      PeopleAtWork obj5= PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.PeopleAtWork.class);
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
 	   
	    
        
        
        //Search with LexID
    	obj3= new Search(driver);
	
		
		obj3.searchlink.click(); 
		Thread.sleep(3000);
	
		obj3.LexID.sendKeys(LexID);
		
		obj3.RefNum.sendKeys(RefNum);
			
		obj3.adv_search.click();
		Thread.sleep(10000);
		
		obj3.identitytab.click();

		obj3.Scorecard.click();
		Thread.sleep(3000);
		String Displayed_ssn = obj3.scorecard_ssn.getText();
	    
	    Assert.assertEquals(Displayed_ssn,ssn);
	    test.log(Status.PASS, "searched with LexID");
        
	   //select CompReport & select Conceal Weapon Permit 
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
		
		
		obj4.peopleatwork.click();
	    obj4.Requestreport.click();
	    obj5 =new PeopleAtWork(driver);
	    Thread.sleep(10000);
	    Boolean container =obj5.Container.isDisplayed();
		System.out.println("People At Work Container present is " +container);
		Assert.assertTrue(container);
		  test.log(Status.PASS, "People At work container present");
		  
		  String T_RefNum=obj5.T_RefNum.getText();
		  String B_RefNum=obj5.B_RefNum.getText();
		  String T_RefNumVal=obj5.T_RefNumVal.getText();
		  String B_RefNumVal=obj5.B_RefNumVal.getText();
		  
		  
		  Assert.assertEquals(T_RefNum, Ref);
		  Assert.assertEquals(B_RefNum, Ref);
		  Assert.assertEquals(T_RefNumVal, RefNum);
		  Assert.assertEquals(B_RefNumVal, B_RefNum1);
		  System.out.println("Refrence Number is present in Comp report  ");
		  test.log(Status.PASS, "Refrence Number Present in Top & bottom of the Comp report is verified");
		  
		Thread.sleep(10000);
		
		String PAW =driver.findElement(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[1]/div[1]/span")).getText();
		System.out.println(PAW);
		int s1=PAW.length();
		System.out.println(s1);
		String num= PAW.substring(16,s1-1);
		int PAW_count=Integer.valueOf(num);
		System.out.println(PAW_count);
		List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[2]/app-people-at-work-widget/div/div[1]/app-collapsible-table-widget/div/table/tbody/tr"));
		int rowcount = rows.size()/2;
		System.out.println("ROW COUNT : "+rowcount);
		Assert.assertEquals(PAW_count, rowcount);
		test.log(Status.PASS, "Verified number of rows with People at work count");
		
		String header1=obj5.header1.getText();
		String header2=obj5.header2.getText();
		String header3=obj5.header3.getText();
		String header4=obj5.header4.getText();
		String header5=obj5.header5.getText();
		String header6=obj5.header6.getText();
		System.out.println(header1  + header2  + header3 + header4);
		
		
		Assert.assertEquals(header1,Header1);
		Assert.assertEquals(header2,Header2);
		Assert.assertEquals(header3,Header3);
		Assert.assertEquals(header4,Header4);
		Assert.assertEquals(header5,Header5);
		Assert.assertEquals(header6,Header6);
		test.log(Status.PASS, " People at work section Headers are verified");
		
		String dname=obj5.name.getText();
		String dtitle=obj5.Title.getText();
		String dcompanyname=obj5.CompanyName.getText();
		String daddress=obj5.Address.getText();
		String dphonenumber=obj5.PhoneNumber.getText();
		String ddates=obj5.Dates.getText();
		
		Assert.assertEquals(dname,Name);
		Assert.assertEquals(dtitle,Title);
		Assert.assertEquals(dcompanyname,CompanyName);
		Assert.assertEquals(daddress,Address);
		Assert.assertEquals(dphonenumber,PhoneNumber);
		Assert.assertEquals(ddates,Dates);
	  obj5.FirstRow.click();
		
	  
	  Boolean PI =obj5.PersonalInfo.isDisplayed();
		Assert.assertTrue(PI);
		Boolean PEI= obj5.CompanyInfo.isDisplayed();
		Assert.assertTrue(PEI);
		System.out.println("PI & CI is present");
		
	 
	 
	     String dPIname=obj5.PI_Name.getText();
	     String dPInameVal=obj5.PI_NameVal.getText();
		 String dPITitle=obj5.PI_Title.getText();
		String dPITitleVal=obj5.PI_TitleVal.getText();
		String dPILexID=obj5.PI_LexID.getText();
		String dPILexIDVal=obj5.PI_LexIDVal.getText();
		String dPISSN=obj5.PI_SSN.getText();
		String dPISSNVal=obj5.PI_SSNVal.getText();
	
		
		
		Assert.assertEquals(dPIname,PI_Name);
		Assert.assertEquals(dPInameVal, PI_NameVal);
		Assert.assertEquals(dPITitle,PI_Title);
		Assert.assertEquals(dPITitleVal,PI_TitleVal);
		Assert.assertEquals(dPILexID,PI_LexiD);
		Assert.assertEquals(dPILexIDVal,PI_LexIdVal);
		Assert.assertEquals(dPISSN,PI_SSN);
		Assert.assertEquals(dPISSNVal,PI_SSNVal);
	
	
		test.log(Status.PASS, "Personal Info Section is Verified");
		
		System.out.println("Personal Info section is verified");
		
		   String dCIcompany=obj5.CI_company.getText();
		   String dCIcompanyVal=obj5.CI_companyVal.getText();
		   String dCIaddress=obj5.CI_Address.getText();
		   String dCIaddressVal=obj5.CI_AddressVal.getText();
		   String dCIphone=obj5.CI_Phonenumber.getText();
		   String dCIphoneVal=obj5.CI_PhonenumberVal.getText();
		   String dCIFEIN=obj5.CI_FEIN.getText();
		   String dCIFEINVal=obj5.CI_FEINVal.getText();
		   String dCIDate=obj5.CI_Dates.getText();
		   String dCIDateVal=obj5.CI_DatesVal.getText();
		   
		   Assert.assertEquals(dCIcompany,CI_Company);
			Assert.assertEquals(dCIcompanyVal, CI_CompanyVal);
			Assert.assertEquals(dCIaddress,CI_Address);
			Assert.assertEquals(dCIaddressVal, CI_AddressVal);
			   
			Assert.assertEquals(dCIphone,CI_PhoneNumber);
			Assert.assertEquals(dCIphoneVal, CI_PhoneNumberVal);  
			Assert.assertEquals(dCIFEIN,CI_FEIN);
			Assert.assertEquals(dCIFEINVal, CI_FEINVal);  
			Assert.assertEquals(dCIDate,CI_Date);
			Assert.assertEquals(dCIDateVal, CI_DateVal);  
		   
			test.log(Status.PASS, "Company Info Section is Verified");
			
			System.out.println("Compnay Info section is verified");
			
		   
		  //Verify + & - Icon
		
			Boolean minus= obj5.minus.isDisplayed();
		System.out.println(minus);
		Assert.assertTrue(minus);
			
	obj5.minus.click();
		Boolean plus= obj5.plus.isDisplayed();
		System.out.println(plus);
		Assert.assertTrue(plus);
		 test.log(Status.PASS, "+ & - Icon is verified");
		  
		
		 test.log(Status.PASS, "People at work Section is Verified");
			
		
		
	 	/*   
	   
		//logout 
		obj6= new logout(driver);
		obj6.logout();
		 logger.log(Status.PASS, "Logged out");*/
		 
	}
        
    
    
    
    @DataProvider
    public Object[][] Authentication() throws Exception{
    	 
        Object[][] testObjArray = ExcelUtils3.getTableArray("C:\\Users\\asokma01\\eclipse-workspace\\selenium-testng-RIN\\src\\test\\resources\\testData\\default.xlsx","RefNum");

        return (testObjArray);

}
  
   

}
 








 







