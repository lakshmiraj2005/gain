package com.lexisnexis.risk.swqa.RIN.ui.test;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
//import org.hibernate.validator.internal.util.logging.Log_.logger;
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
import com.lexisnexis.risk.swqa.RIN.ui.pages.Conceal;
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

public class CR_CWP_TEST extends BaseTest {
	
	 
	 
		
    @Test(dataProvider="Authentication")
      public void Comp_report_PA(String Browser,String username,String	password,String	name,String	LexID,String ssn,String	Header1,String	Header2,String	Header3,String	Header4,
    		  String Name,String	PermitNum,String	PermitType,String	ExpirationDate,String	PI_name,String	PI_Address,String	PI_Lexid,String	PI_DOB,String	PI_Gender,String	PI_Ethnicity,
    		  String	PI_nameVal,String	PI_AddressVal,String	PI_DOBVal,String	PI_GenderVal,String	PI_EthinicityVal,String	PEI_PermitState,String	PEI_PermitNum,String	PEI_PermitType,String	PEI_ExpDate,
    		  String	PEI_PermitStateVal,String	PEI_PermitNumVal,String	PEI_PermitTypeVal,String	PEI_ExpDateVal ) throws Throwable {
    	
    	
    	 test=extent.createTest("Comp Report Concealed Weapons Permits");
    	login obj1 = PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.login.class);
        Dashboard obj2 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Dashboard.class);
        Search obj3 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Search.class);
        CompReport obj4=PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.CompReport.class);
        Conceal obj5= PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Conceal.class);
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
			
		obj3.adv_search.click();
		Thread.sleep(10000);
		
		obj3.identitytab.click();

		obj3.Scorecard.click();
		Thread.sleep(3000);
		String Displayed_ssn = obj3.scorecard_ssn.getText();
	    
	    Assert.assertEquals(ssn,Displayed_ssn);
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
		
		
		obj4.weapon_permit.click();
	    obj4.Requestreport.click();
	    obj5 =new Conceal(driver);
	    Thread.sleep(10000);
	    Boolean container =obj5.Container.isDisplayed();
		System.out.println("Conceal Weapon Permit Container present is " +container);
		Assert.assertTrue(container);
		  test.log(Status.PASS, "Verified Conceal Weapon Permit container present");
		Thread.sleep(10000);
		
		String CWP =driver.findElement(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[1]/div[1]/span")).getText();
		System.out.println(CWP);
		int s1=CWP.length();
		System.out.println(s1);
		String num= CWP.substring(27,s1-1);
		int CWP_count=Integer.valueOf(num);
		System.out.println(CWP_count);
		List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[2]/app-weapon-permit-widget/div/div[1]/app-collapsible-table-widget/div/table/tbody/tr"));
		int rowcount = rows.size()/2;
		System.out.println("ROW COUNT : "+rowcount);
		Assert.assertEquals(CWP_count, rowcount);
		test.log(Status.PASS, "Verified number of rows with Conceal Weapons Permits count");
		
		String header1=obj5.header1.getText();
		String header2=obj5.header2.getText();
		String header3=obj5.header3.getText();
		String header4=obj5.header4.getText();
		System.out.println(header1  + header2  + header3 + header4);
		
		
		Assert.assertEquals(header1,Header1);
		Assert.assertEquals(header2,Header2);
		Assert.assertEquals(header3,Header3);
		Assert.assertEquals(header4,Header4);
		
		test.log(Status.PASS, " Conceal Weapons Permit section Headers are verified");
		
		String dname=obj5.name.getText();
		String dpermitnum=obj5.permitnum.getText();
		String dpermittype=obj5.permittype.getText();
		String dExpDate=obj5.ExpDate.getText();
		
		Assert.assertEquals(dname,Name);
		Assert.assertEquals(dpermitnum,PermitNum);
		Assert.assertEquals(dpermittype,PermitType);
		Assert.assertEquals(dExpDate,ExpirationDate);
		
		obj5.FirstRow.click();
		Boolean PI =obj5.PersonalInfo.isDisplayed();
		Assert.assertTrue(PI);
		Boolean PermitI= obj5.PermitInfo.isDisplayed();
		Assert.assertTrue(PermitI);
		System.out.println("PersonalInfo & PermitInfo are present");
		
		 String dPI_Name=obj5.PI_name.getText();
		String dPI_Lexid=obj5.PI_LexID.getText();
		String dPI_Address=obj5.PI_address.getText();
		String dPI_DOB=obj5.PI_DOB.getText();
		String dPI_Gender=obj5.PI_Gender.getText();
		String dPI_Ethnicity=obj5.PI_Ethnicity.getText();
		
		Assert.assertEquals(dPI_Name,PI_name);
		Assert.assertEquals(dPI_Lexid,PI_Lexid);
		Assert.assertEquals(dPI_Address,PI_Address);
		Assert.assertEquals(dPI_DOB,PI_DOB);
		Assert.assertEquals(dPI_Gender,PI_Gender);
		Assert.assertEquals(dPI_Ethnicity,PI_Ethnicity);
		
		
		String dPI_NameVal=obj5.PI_nameVal.getText();
		String dPI_LexidVal=obj5.PI_LexIDVal.getText();
		String dPI_AddressVal=obj5.PI_addressVal.getText();
		String dPI_DOBVal=obj5.PI_DOBVal.getText();
		String dPI_GenderVal=obj5.PI_GenderVal.getText();
		String dPI_EthnicityVal=obj5.PI_EthnicityVal.getText();
							
		Assert.assertEquals(dPI_NameVal,PI_nameVal);
		Assert.assertEquals(dPI_LexidVal,LexID);
		Assert.assertEquals(dPI_AddressVal,PI_AddressVal);
		Assert.assertEquals(dPI_DOBVal,PI_DOBVal);
		Assert.assertEquals(dPI_GenderVal,PI_GenderVal);
		Assert.assertEquals(dPI_EthnicityVal,PI_EthinicityVal);
		test.log(Status.PASS, " Personal Information sections is verified");
		
		
 
		
		String dPermit_PS= obj5.Permit_PS.getText();
		String dPermit_PSVal=obj5.Permit_PSVal.getText();
		String dPermit_PN= obj5.Permit_PN.getText();
		String dPermit_PNVal=obj5.Permit_PNVal.getText();
		String dPermit_PT = obj5.Permit_PT.getText();
		String dPermit_PTVal=obj5.Permit_PTVal.getText();
		String dPermit_ED = obj5.Permit_ED.getText();
		String dPermit_EDVal=obj5.Permit_EDVal.getText();
	
		
		Assert.assertEquals(dPermit_PS,PEI_PermitState);
		Assert.assertEquals(dPermit_PSVal,PEI_PermitStateVal);
		Assert.assertEquals(dPermit_PN, PEI_PermitNum);
		Assert.assertEquals(dPermit_PNVal, PEI_PermitNumVal);
		Assert.assertEquals(dPermit_PT, PEI_PermitType);
		Assert.assertEquals(dPermit_PTVal, PEI_PermitTypeVal);
		Assert.assertEquals(dPermit_ED,PEI_ExpDate);
		Assert.assertEquals(dPermit_EDVal,PEI_ExpDateVal);
		
	
		test.log(Status.PASS, "Permit Information Section is Verified");
		
		System.out.println("Concealed weapons Permit  section is verified");
		
		
		 
		  //Verify + & - Icon
		
			Boolean minus= obj5.minus.isDisplayed();
		System.out.println(minus);
		Assert.assertTrue(minus);
			
	obj5.minus.click();
		Boolean plus= obj5.plus.isDisplayed();
		System.out.println(plus);
		Assert.assertTrue(plus);
		 test.log(Status.PASS, "+ & - Icon is verified");
		  
		
		
		
		  test.log(Status.PASS, "Concealed weapons Permit  section is verified");
	 	   
	   
		//logout 
		obj6= new logout(driver);
		obj6.logout();
		 test.log(Status.PASS, "Logged out");
		 
	}
        
    
    
    
    @DataProvider
    public Object[][] Authentication() throws Exception{
    	 
        Object[][] testObjArray = ExcelUtils3.getTableArray("C:\\Users\\asokma01\\eclipse-workspace\\selenium-testng-RIN\\src\\test\\resources\\testData\\default.xlsx","CWP");

        return (testObjArray);

}
  
   

}
 








 




