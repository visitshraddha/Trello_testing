package com.trello.testcase;

import com.trello.basetest.BaseTest;
import com.trello.basetest.Xls_Reader;
import com.trello.util.DataReader;
import com.trello.util.ExtentManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class Login_Tc extends BaseTest {
    public String TC="Login_TC";
    public Xls_Reader exls=null;


    @Test(dataProvider="tcdata")
    public void Login_Tc(Hashtable<String, String>ht){
        report=ExtentManager.getInstance();
        test=report.startTest("Login_Tc"+ ht.toString());
        loadconfig();

        if(!DataReader.isRunnable(exls, "Suite", TC) || ht.get("RunMode").equals("N")){
            test.log(LogStatus.SKIP, TC +" is passed");
            throw new SkipException("skip testcase as runmode flag is 'N'");
        }

        launchbrowser("Chrome");
        test.log(LogStatus.INFO, "Browser launch successfully");
        driver.navigate().to(ht.get("URL"));
        test.log(LogStatus.INFO, "Trello page is launch successfully");

        dynamicwait(30, "Trello_xpath");
        takescreenshot(test);


        settext("Email_xpath",ht.get("Email"));
        settext("password_xpath",ht.get("PWD"));
        click("Loginbutton_xpath");

        dynamicwait(30, "Board_xpath");
        takescreenshot(test);
       test.log(LogStatus.PASS, "User logged in successfully");





    }
    @AfterMethod
    public void teardowbn(){
        report.endTest(test);
        report.flush();



    }

    @AfterClass
    public void close(){
        if(driver!=null)
            driver.quit();

    }





    @DataProvider(name="tcdata")
    public Object[][] tcdata(){
        exls= new Xls_Reader(System.getProperty("user.dir")+"\\TestData\\Trello_TestData.xlsx");

         return DataReader.getdata(exls,"TestData",TC);

    }

}
