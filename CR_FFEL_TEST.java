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
import com.lexisnexis.risk.swqa.RIN.ui.pages.firearms;
import com.lexisnexis.risk.swqa.RIN.ui.pages.launch;
import com.lexisnexis.risk.swqa.RIN.ui.pages.login;

import com.lexisnexis.risk.swqa.RIN.ui.pages.logout;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
//import com.sun.jna.platform.FileUtils;

public class CR_FFEL_TEST extends BaseTest {
	
		   
		
    @Test(dataProvider="Authentication")
      public void Comp_report_PA(String Browser,String username,String	password,String	name,String	LexID,String ssn,String	Header1,String	Header2,String	Header3,String	Header4,
    		  String Name,String LicenseNum,String LicenseType,String	ExpirationDate,String PI_name,String PI_Lexid,String PI_Address,String PI_nameVal,String PI_AddressVal,
    		  String LI_Company,String	LI_LicenseNum,String LI_LicenseType,String	LI_IssueState,String LI_LExpDate,String LI_CompanyVal,String LI_LicenseNumVal,String LI_LicenseTypeVal,
    		  String LI_IssueStateVal,String LI_LicenseExpDateVal ) throws Throwable {
    	
    	test=extent.createTest("Comp Report Federal Firearms & Explosive Licenses");
    	
    	login obj1 = PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.login.class);
        Dashboard obj2 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Dashboard.class);
        Search obj3 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Search.class);
        CompReport obj4=PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.CompReport.class);
        firearms obj5= PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.firearms.class);
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
 	   
	    
        
        
        //Search with LexID
    	obj3= new Search(driver);
	
		
		obj3.searchlink.click(); 
		Thread.sleep(3000);
	
		obj3.LexID.sendKeys(LexID);
			
		obj3.adv_search.click();
		Thread.sleep(10000);
		
		obj3.identitytab.click();

		obj3.Scorecard.click();
		Thread.sleep(3000);
		String Displayed_ssn = obj3.scorecard_ssn.getText();
	    
	    Assert.assertEquals(ssn,Displayed_ssn);
	    test.log(Status.PASS, "searched with LexID");
        
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
		
		
		obj4.firearms.click();
	    obj4.Requestreport.click();
	    obj5 =new firearms(driver);
	    Thread.sleep(10000);
	    Boolean container =obj5.Container.isDisplayed();
		System.out.println("Firearms Container present is " +container);
		Assert.assertTrue(container);
		  test.log(Status.PASS, "Verified Federal Firearms and Explosives License container present");
		Thread.sleep(10000);
		
		String FFEL =driver.findElement(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[1]/div[1]/span")).getText();
		System.out.println(FFEL);
		int s1=FFEL.length();
		System.out.println(s1);
		String num= FFEL.substring(42,s1-1);
		int FFEL_count=Integer.valueOf(num);
		System.out.println(FFEL_count);
		List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[2]/app-firearms-widget/div/div[1]/app-collapsible-table-widget/div/table/tbody/tr"));
		int rowcount = rows.size()/2;
		System.out.println("ROW COUNT : "+rowcount);
		Assert.assertEquals(FFEL_count, rowcount);
		test.log(Status.PASS, "Verified number of rows with Federal Firearms and Explosives License count");
		
		String header1=obj5.header1.getText();
		String header2=obj5.header2.getText();
		String header3=obj5.header3.getText();
		String header4=obj5.header4.getText();
		System.out.println(header1  + header2  + header3 + header4);
		
		
		Assert.assertEquals(header1,Header1);
		Assert.assertEquals(header2,Header2);
		Assert.assertEquals(header3,Header3);
		Assert.assertEquals(header4,Header4);
		
		test.log(Status.PASS, " Federal Firearms and Explosives License sections Headers are verified");
		
		String dname=obj5.name.getText();
		String dlicennum=obj5.LicenNum.getText();
		String dLicentype=obj5.LicenType.getText();
		String dExpDate=obj5.ExpDate.getText();
		
		Assert.assertEquals(dname,Name);
		Assert.assertEquals(dlicennum,LicenseNum);
		Assert.assertEquals(dLicentype,LicenseType);
		Assert.assertEquals(dExpDate,ExpirationDate);
		
		obj5.FirstRow.click();
		Boolean PI =obj5.PersonalInfo.isDisplayed();
		Assert.assertTrue(PI);
		Boolean LI= obj5.LicenseInfo.isDisplayed();
		Assert.assertTrue(LI);
		System.out.println("PI & LI is present");
		
		 String dPI_Name=obj5.PI_name.getText();
		String dPI_Lexid=obj5.PI_LexID.getText();
		String dPI_Address=obj5.PI_Address.getText();
		
		Assert.assertEquals(dPI_Name,PI_name);
		Assert.assertEquals(dPI_Lexid,PI_Lexid);
		Assert.assertEquals(dPI_Address,PI_Address);
		
		String dPI_NameVal=obj5.PI_nameVal.getText();
		String dPI_LexidVal=obj5.PI_LexIDVal.getText();
		String dPI_AddressVal=obj5.PI_AddressVal.getText();
	
							
		Assert.assertEquals(dPI_NameVal,PI_nameVal);
		Assert.assertEquals(dPI_LexidVal,LexID);
		Assert.assertEquals(dPI_AddressVal,PI_AddressVal);
		
		test.log(Status.PASS, " Personal Information sections is verified");
		
		
 
		
		String dLICompany= obj5.LI_Company.getText();
		String dLICompanyVal=obj5.LI_CompanyVal.getText();
		String dLILicenseNum= obj5.LI_LicenseNum.getText();
		String dLILicenseNumVal=obj5.LI_LicenseNumVal.getText();
		String dLILicenseType = obj5.LI_LicenseType.getText();
		String dLILicenseTypeVal=obj5.LI_LicenseTypeVal.getText();
		String dLIIssueState = obj5.LI_IssueState.getText();
		String dLIIssueStateVal=obj5.LI_IssueStateVal.getText();
		String dLILExpDate=obj5.LI_LicenseExpDate.getText();
		String dLIExpDateVal=obj5.LI_LicenseExpDateVal.getText();
		
		Assert.assertEquals(dLICompany,LI_Company);
		Assert.assertEquals(dLICompanyVal,LI_CompanyVal);
		Assert.assertEquals(dLILicenseNum, LI_LicenseNum);
		Assert.assertEquals(dLILicenseNumVal, LI_LicenseNumVal);
		Assert.assertEquals(dLILicenseType, LI_LicenseType);
		Assert.assertEquals(dLILicenseTypeVal,LI_LicenseTypeVal);
		//Assert.assertEquals(dLIIssueState,LI_IssueState);
		//Assert.assertEquals(dLIIssueStateVal,LI_IssueStateVal);
		Assert.assertEquals(dLILExpDate, LI_LExpDate);
		Assert.assertEquals(dLIExpDateVal,LI_LicenseExpDateVal);

		test.log(Status.PASS, "License Information Section is Verified");
		
		System.out.println("Federal and Firearm section is verified");
		
		
		 
		  //Verify + & - Icon
		
			Boolean minus= obj5.minus.isDisplayed();
		System.out.println(minus);
		Assert.assertTrue(minus);
			
	obj5.minus.click();
		Boolean plus= obj5.plus.isDisplayed();
		System.out.println(plus);
		Assert.assertTrue(plus);
		 test.log(Status.PASS, "+ & - Icon is verified");
		  
		
		
		
		  test.log(Status.PASS, "Federal and Firearm section is verified");
	 	/*   
	   
		//logout 
		obj6= new logout(driver);
		obj6.logout();
		 logger.log(Status.PASS, "Logged out");*/
		 
	}
        
    
    
    
    @DataProvider
    public Object[][] Authentication() throws Exception{
    	 
        Object[][] testObjArray = ExcelUtils3.getTableArray("C:\\Users\\asokma01\\eclipse-workspace\\selenium-testng-RIN\\src\\test\\resources\\testData\\default.xlsx","FFEL");

        return (testObjArray);

}
  
  
}
 








 




