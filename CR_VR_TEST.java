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
import com.lexisnexis.risk.swqa.RIN.ui.pages.Voter;
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

public class CR_VR_TEST extends BaseTest {
	
	 
	 
		
    @Test(dataProvider="Authentication")
      public void Comp_report_PA(String Browser,String username,String	password,String	name,String	LexID,String ssn,String	Header1,String	Header2,String	Header3,String	Header4,
    		 String Header5,String	Name,String Status1,String	LastVoteDate,String	StateofRegistration,String	PoliticalParty,String	Address,String	Lexid,String DOB,
    		 String	Gender,String	Ethnicity,String AddressVal,String	DOBVal,String	GenderVal,String	EthnicityVal ) throws Throwable {
    	    	
    	 test=extent.createTest("Comp Report Voter Registration");
    	login obj1 = PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.login.class);
        Dashboard obj2 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Dashboard.class);
        Search obj3 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Search.class);
        CompReport obj4=PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.CompReport.class);
      Voter obj5= PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Voter.class);
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
		
		
		obj4.voter.click();
	    obj4.Requestreport.click();
	    obj5 =new Voter(driver);
	    Thread.sleep(10000);
	    Boolean container =obj5.Container.isDisplayed();
		System.out.println("Voter Registration Container present is " +container);
		Assert.assertTrue(container);
		  test.log(Status.PASS, "Verified  Voter Registration container present");
		Thread.sleep(10000);
		
		String VR =driver.findElement(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[1]/div[1]/span")).getText();
		System.out.println(VR);
		int s1=VR.length();
		System.out.println(s1);
		String num= VR.substring(20,s1-1);
		int VR_count=Integer.valueOf(num);
		System.out.println(VR_count);
		List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[2]/app-voter-registration-widget/div/div[1]/app-collapsible-table-widget/div/table/tbody/tr"));
		int rowcount = rows.size()/2;
		System.out.println("ROW COUNT : "+rowcount);
		Assert.assertEquals(VR_count, rowcount);
		test.log(Status.PASS, "Verified number of rows with Voter Registration count");
		
		String header1=obj5.header1.getText();
		String header2=obj5.header2.getText();
		String header3=obj5.header3.getText();
		String header4=obj5.header4.getText();
		String header5=obj5.header5.getText();
		System.out.println(header1  + header2  + header3 + header4);
		
		
		Assert.assertEquals(header1,Header1);
		Assert.assertEquals(header2,Header2);
		Assert.assertEquals(header3,Header3);
		Assert.assertEquals(header4,Header4);
		Assert.assertEquals(header5,Header5);
		test.log(Status.PASS, " Voter Registration section Headers are verified");
		
		String dname=obj5.name.getText();
		String dstatus1=obj5.Status1.getText();
		String dLastVoteDate=obj5.LastVoteDate.getText();
		String dStateofReg=obj5.StateofReg.getText();
		String dpoliticalparty=obj5.PolitalParty.getText();
		
		Assert.assertEquals(dname,Name);
		Assert.assertEquals(dstatus1,Status1);
		Assert.assertEquals(dLastVoteDate,LastVoteDate);
		Assert.assertEquals(dStateofReg,StateofRegistration);
		Assert.assertEquals(dpoliticalparty,PoliticalParty);
		
		
		obj5.FirstRow.click();
		
		
		 String dAddress=obj5.Address.getText();
		String dAddressVal=obj5.AddressVal.getText();
		String dLexID=obj5.LexID.getText();
		String dLexIDVal=obj5.LexIDVal.getText();
		String dDOB=obj5.DOB.getText();
		String dDOBVal=obj5.DOBVal.getText();
		String dGender=obj5.Gender.getText();
		String dGenderVal=obj5.GenderVal.getText();
		String dEthnicity=obj5.Ethnicity.getText();
		String dEthnicityVal=obj5.EthnicityVal.getText();
		
		Assert.assertEquals(dAddress,Address);
		Assert.assertEquals(dAddressVal,AddressVal);
		Assert.assertEquals(dLexID,Lexid);
		Assert.assertEquals(dLexIDVal,LexID);
		Assert.assertEquals(dGender,Gender);
		Assert.assertEquals(dGenderVal,GenderVal);
		Assert.assertEquals(dDOB,DOB);
		Assert.assertEquals(dDOBVal,DOBVal);
		Assert.assertEquals(dEthnicity,Ethnicity);
		Assert.assertEquals(dEthnicityVal,EthnicityVal);
		
		
		
 	
		
	
		test.log(Status.PASS, "Voter Registration Section is Verified");
		
		System.out.println("Voter Registration section is verified");
		
		
		 
		  //Verify + & - Icon
		
			Boolean minus= obj5.minus.isDisplayed();
		System.out.println(minus);
		Assert.assertTrue(minus);
			
	obj5.minus.click();
		Boolean plus= obj5.plus.isDisplayed();
		System.out.println(plus);
		Assert.assertTrue(plus);
		 test.log(Status.PASS, "+ & - Icon is verified");
		  
		
		
		
		
	 	/*   
	   
		//logout 
		obj6= new logout(driver);
		obj6.logout();
		 logger.log(Status.PASS, "Logged out");*/
		 
	}
        
    
    
    
    @DataProvider
    public Object[][] Authentication() throws Exception{
    	 
        Object[][] testObjArray = ExcelUtils3.getTableArray("C:\\Users\\asokma01\\eclipse-workspace\\selenium-testng-RIN\\src\\test\\resources\\testData\\default.xlsx","VR");

        return (testObjArray);

}
  
   

}
 








 





