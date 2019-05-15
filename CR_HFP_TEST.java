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
import com.lexisnexis.risk.swqa.RIN.ui.pages.Huntfish;
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

public class CR_HFP_TEST extends BaseTest {
	
	 
	 
		
    @Test(dataProvider="Authentication")
      public void Comp_report(String Browser,String username,String	password,String	name,String	LexID,String ssn,String	Header1,String	Header2,String	Header3,String	Header4,
    		 String Header5,String	Name,String PermitType,String	PermitNum,String	PermitDate,String	PermitState,String	PI_Name,String	PI_NameVal,String	PI_Address,
    		 String	PI_AddressVal,String	PI_LexiD,String	PI_DOB,String	PI_DOBVal,String	PI_Gender,String	PI_GenderVal,String PI_Ethnicity,String PI_EthnicityVal,String	PEI_PermitDate,String	PEI_PermitDateVal,
    		 String	PEI_PermitNum,String	PEI_PermitNumVal,String	PEI_PermitType,String	PEI_PermitTypeVal,String	PEI_HomeState,String	PEI_HomeStateVal,String	PEI_PermitState,String	PEI_PermitStateVal) throws Throwable {
    	    	
    	 test=extent.createTest("Comp Report Hunter/Fishing Permits");
    	login obj1 = PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.login.class);
        Dashboard obj2 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Dashboard.class);
        Search obj3 =PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Search.class);
        CompReport obj4=PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.CompReport.class);
      Huntfish obj5= PageFactory.initElements(driver, com.lexisnexis.risk.swqa.RIN.ui.pages.Huntfish.class);
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
		
		
		obj4.huntfish.click();
	    obj4.Requestreport.click();
	    obj5 =new Huntfish(driver);
	    Thread.sleep(10000);
	    Boolean container =obj5.Container.isDisplayed();
		System.out.println("Hunt/Fish Container present is " +container);
		Assert.assertTrue(container);
		  test.log(Status.PASS, "Hunt/Fish container present");
		Thread.sleep(10000);
		
		String HF =driver.findElement(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[1]/div[1]/span")).getText();
		System.out.println(HF);
		int s1=HF.length();
		System.out.println(s1);
		String num= HF.substring(25,s1-1);
		int HF_count=Integer.valueOf(num);
		System.out.println(HF_count);
		List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"grid-container\"]/gridster-item[2]/app-widget-container/div/div[2]/app-hunting-fishing-widget/div/div[1]/app-collapsible-table-widget/div/table/tbody/tr"));
		int rowcount = rows.size()/2;
		System.out.println("ROW COUNT : "+rowcount);
		Assert.assertEquals(HF_count, rowcount);
		test.log(Status.PASS, "Verified number of rows with Hunt/Fish count");
		
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
		test.log(Status.PASS, " Hunt/Fish section Headers are verified");
		
		String dname=obj5.name.getText();
		String dpermittype=obj5.permittype.getText();
		String dpermitnum=obj5.permitnum.getText();
		String dpermitdate=obj5.permitdate.getText();
		String dpermitstate=obj5.permitstate.getText();
		
		Assert.assertEquals(dname,Name);
		Assert.assertEquals(dpermittype,PermitType);
		Assert.assertEquals(dpermitnum,PermitNum);
		Assert.assertEquals(dpermitdate,PermitDate);
		Assert.assertEquals(dpermitstate,PermitState);
		
	  obj5.FirstRow.click();
		
	  
	  Boolean PI =obj5.PersonalInfo.isDisplayed();
		Assert.assertTrue(PI);
		Boolean PEI= obj5.PermitInfo.isDisplayed();
		Assert.assertTrue(PEI);
		System.out.println("PI & PEI is present");
		
	 
	 
	     String dPIname=obj5.PI_name.getText();
	     String dPInameVal=obj5.PI_nameVal.getText();
		 String dPIAddress=obj5.PI_address.getText();
		String dPIAddressVal=obj5.PI_addressVal.getText();
		String dPILexID=obj5.PI_LexID.getText();
		String dPILexIDVal=obj5.PI_LexIDVal.getText();
		String dPIDOB=obj5.PI_DOB.getText();
		String dPIDOBVal=obj5.PI_DOBVal.getText();
		String dPIGender=obj5.PI_Gender.getText();
		String dPIGenderVal=obj5.PI_GenderVal.getText();
		String dPIEthnicity=obj5.PI_Ethnicity.getText();
		String dPIEthnicityVal=obj5.PI_EthnicityVal.getText();
		
		
		Assert.assertEquals(dPIname,PI_Name);
		Assert.assertEquals(dPInameVal, PI_NameVal);
		Assert.assertEquals(dPIAddress,PI_Address);
		Assert.assertEquals(dPIAddressVal,PI_AddressVal);
		Assert.assertEquals(dPILexID,PI_LexiD);
		Assert.assertEquals(dPILexIDVal,LexID);
		Assert.assertEquals(dPIGender,PI_Gender);
		Assert.assertEquals(dPIGenderVal,PI_GenderVal);
		Assert.assertEquals(dPIDOB,PI_DOB);
		Assert.assertEquals(dPIDOBVal,PI_DOBVal);
		Assert.assertEquals(dPIEthnicity,PI_Ethnicity);
		Assert.assertEquals(dPIEthnicityVal,PI_EthnicityVal);
				
	
		test.log(Status.PASS, "Personal Info Section is Verified");
		
		System.out.println("Personal Info section is verified");
		
		   String dPEIDate=obj5.PEI_PermitDate.getText();
		   String dPEIDateVal=obj5.PEI_PermitDateVal.getText();
		   String dPEIPermitNum=obj5.PEI_PermitNum.getText();
		   String dPEIPermitNumVal=obj5.PEI_PermitNumVal.getText();
		   String dPEIPermitType=obj5.PEI_PermitType.getText();
		   String dPEIPermitTypeVal=obj5.PEI_PermitTypeVal.getText();
		   String dPEIHomeState=obj5.PEI_HomeState.getText();
		   String dPEIHomeStateVal=obj5.PEI_HomeStateVal.getText();
		   String dPEIPermitState=obj5.PEI_PermitState.getText();
		   String dPEIPermitStateVal=obj5.PEI_PermitStateVal.getText();
		   
		   Assert.assertEquals(dPEIDate,PEI_PermitDate);
			Assert.assertEquals(dPEIDateVal, PEI_PermitDateVal);
			Assert.assertEquals(dPEIPermitNum,PEI_PermitNum);
			Assert.assertEquals(dPEIPermitNumVal, PEI_PermitNumVal);
			   
			Assert.assertEquals(dPEIPermitType,PEI_PermitType);
			Assert.assertEquals(dPEIPermitTypeVal, PEI_PermitTypeVal);  
			Assert.assertEquals(dPEIHomeState,PEI_HomeState);
			Assert.assertEquals(dPEIHomeStateVal, PEI_HomeStateVal);  
			Assert.assertEquals(dPEIPermitState,PEI_PermitState);
			Assert.assertEquals(dPEIPermitStateVal, PEI_PermitStateVal);  
		   
			test.log(Status.PASS, "Permit Info Section is Verified");
			
			System.out.println("Permit Info section is verified");
			
		   
		  //Verify + & - Icon
		
			Boolean minus= obj5.minus.isDisplayed();
		System.out.println(minus);
		Assert.assertTrue(minus);
			
	obj5.minus.click();
		Boolean plus= obj5.plus.isDisplayed();
		System.out.println(plus);
		Assert.assertTrue(plus);
		 test.log(Status.PASS, "+ & - Icon is verified");
		  
		
		 test.log(Status.PASS, "Hunting/Fishing Section is Verified");
			
		
		
	 	/*   
	   
		//logout 
		obj6= new logout(driver);
		obj6.logout();
		 logger.log(Status.PASS, "Logged out");*/
		 
	}
        
    
    
    
    @DataProvider
    public Object[][] Authentication() throws Exception{
    	 
        Object[][] testObjArray = ExcelUtils3.getTableArray("C:\\Users\\asokma01\\eclipse-workspace\\selenium-testng-RIN\\src\\test\\resources\\testData\\default.xlsx","HFP");

        return (testObjArray);

}
  
   

}
 








 






