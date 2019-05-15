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

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.support.PageFactory;
import com.lexisnexis.risk.swqa.RIN.ui.pages.CompReport;
import com.lexisnexis.risk.swqa.RIN.ui.pages.Dashboard;
import com.lexisnexis.risk.swqa.RIN.ui.pages.DriverLicense;
import com.lexisnexis.risk.swqa.RIN.ui.pages.ProfessionalLicense;
import com.lexisnexis.risk.swqa.RIN.ui.pages.Search;

import com.lexisnexis.risk.swqa.RIN.ui.pages.login;

import com.lexisnexis.risk.swqa.RIN.ui.pages.logout;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
//import com.sun.jna.platform.FileUtils;

public class CR_PL_TEST extends BaseTest {
	
		    
		
    @Test(dataProvider="Authentication")
      public void comp_report_DL(String Browser ,String username, String password, String name, String ssn, String Header1, String Header2, String Header3, String Header4,
    		  String Name,String LicenseNum, String LicenseType,String ExpDate,String PI_name,String PI_LA,String PI_nameVal,String PI_LAVal, String LI_LN, String LI_LT,
    		  String LI_PB,String LI_LS,String LI_ED,String LI_LNVal,String LI_LTVal,String LI_PBVal,String LI_LSVal,String LI_EDVal) throws Throwable {
    	
    	test=extent.createTest("Comp Report Professional License");
    	
    	login obj1 = PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.login.class);
        Dashboard obj2 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Dashboard.class);
        Search obj3 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Search.class);
        CompReport obj4=PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.CompReport.class);
        ProfessionalLicense obj5= PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.ProfessionalLicense.class);
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
		
		
		obj4.ProfLicense.click();
	    obj4.Requestreport.click();
	    obj5 =new ProfessionalLicense(driver);
	    
	    Boolean container =obj5.Container.isDisplayed();
		System.out.println("Professional License Container present is " +container);
		Assert.assertTrue(container);
	
		  test.log(Status.PASS, "Verified Professional License container present");
		Thread.sleep(10000);
		
		
		
		String PL =driver.findElement(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[1]/div[1]/span")).getText();
		System.out.println(PL);
		int s1=PL.length();
		System.out.println(s1);
		String num= PL.substring(23,s1-1);
		int PL_count=Integer.valueOf(num);
		System.out.println(PL_count);
		List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[2]/app-professional-license-widget/div/div[1]/app-collapsible-table-widget/div/table/tbody/tr"));
		int rowcount = rows.size()/2;
		System.out.println("ROW COUNT : "+rowcount);
		Assert.assertEquals(PL_count, rowcount);
		test.log(Status.PASS, "Verified number of rows with Prfessional License count");
		
		
		
		
		
		String header1=obj5.header1.getText();
		String header2=obj5.header2.getText();
		String header3=obj5.header3.getText();
		String header4=obj5.header4.getText();
		
		Assert.assertEquals(header1,Header1);
		Assert.assertEquals(header2,Header2);
		Assert.assertEquals(header3,Header3);
		Assert.assertEquals(header4,Header4);
		
	    test.log(Status.PASS, "All the Headers are verified");
	    
		String dname=obj5.name.getText();
		String dLNum=obj5.Lnum.getText();
		String dLType=obj5.Ltype.getText();
		String dExpDate = obj5.Exp_Date.getText();
		
		Assert.assertEquals(dname,Name);
		Assert.assertEquals(dLNum,LicenseNum);
		Assert.assertEquals(dLType,LicenseType);
		Assert.assertEquals(dExpDate,ExpDate);
		
		obj5.Firstrow.click();
		Boolean PI =obj5.PersonalInfo.isDisplayed();
		Assert.assertTrue(PI);
		Boolean LI= obj5.LicenseInfo.isDisplayed();
		Assert.assertTrue(LI);
		System.out.println("PI & LI is present");
		
		String dPIName=obj5.PIName.getText();
		String dPILA=obj5.PILA.getText();
		String dPINameVal=obj5.PINameVal.getText();
		String dPILAVal=obj5.PILAVal.getText();
		
		
							
		Assert.assertEquals(dPIName,PI_name);
		Assert.assertEquals(dPILA,PI_LA);
		Assert.assertEquals(dPINameVal,PI_nameVal);
		Assert.assertEquals(dPILAVal, PI_LAVal);
		
		
		System.out.println("Personal Information is verified ");
		 test.log(Status.PASS, "Personal Information is verified");
		
		String dLILnum= obj5.LILNum.getText();
		String dLILtype= obj5.LILType.getText();
		String dLIPB = obj5.LIPB.getText();
		String dLIS = obj5.LILS.getText();
		String dLIED=obj5.LIED.getText();
		
		String dLILnumval= obj5.LILNumVal.getText();
		String dLILtypeval = obj5.LILTypeVal.getText();
		String dLIPBval=obj5.LIPBVal.getText();
		String dLISval=obj5.LILSVal.getText();
		String dLIEDval=obj5.LIEDVal.getText();
		
		Assert.assertEquals(dLILnum, LI_LN);
		Assert.assertEquals(dLILtype, LI_LT);
		Assert.assertEquals(dLIPB, LI_PB);
		//Assert.assertEquals(dLIS,LI_LS);
		Assert.assertEquals(dLIED,LI_ED);
		
		Assert.assertEquals(dLILnumval,LI_LNVal);
		Assert.assertEquals(dLILtypeval,LI_LTVal);
		Assert.assertEquals(dLIPBval,LI_PBVal);
		Assert.assertEquals(dLISval,LI_LSVal);
		Assert.assertEquals(dLIEDval,LI_EDVal);
		
		
		
		
		test.log(Status.PASS, "License Information Section is Verified");
		
		System.out.println("Professional License section is verified");
		
		
		 
		  //Verify + & - Icon
		
			Boolean minus= obj5.minus.isDisplayed();
		System.out.println(minus);
		Assert.assertTrue(minus);
			
	obj5.minus.click();
		Boolean plus= obj5.plus.isDisplayed();
		System.out.println(plus);
		Assert.assertTrue(plus);
		 test.log(Status.PASS, "+ & - Icon is verified");
		  
		
		
		
		  test.log(Status.PASS, "Professional License  section is verified");
	 	/*   
		//logout 
		obj6= new logout(driver);
		obj6.logout();
		 logger.log(Status.PASS, "Logged out");*/
	}
        
    
    
    
    @DataProvider
    public Object[][] Authentication() throws Exception{
    	 
        Object[][] testObjArray = ExcelUtils3.getTableArray("C:\\Users\\asokma01\\eclipse-workspace\\selenium-testng-RIN\\src\\test\\resources\\testData\\default.xlsx","PL");

        return (testObjArray);

}
  
    

 
 
 
}



 








 




