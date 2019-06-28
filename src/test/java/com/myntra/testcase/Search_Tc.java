package com.myntra.testcase;

import com.myntra.basetest.Xls_Reader;
import com.myntra.util.DataReader;
import com.myntra.util.ExtentManager;
import com.myntra.basetest.BaseTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class Search_Tc extends BaseTest {
    public String TC="Search_Tc";
    public Xls_Reader exls=null;


    @Test(dataProvider="tcdata")
    public void Create_Tc(Hashtable<String, String>ht){
        report=ExtentManager.getInstance();
        test=report.startTest("Search_Tc"+ ht.toString());
        loadconfig();

        if(!DataReader.isRunnable(exls, "Suite", TC) || ht.get("RunMode").equals("N")){
            test.log(LogStatus.SKIP, TC +" is passed");
            throw new SkipException("skip testcase as runmode flag is 'N'");
        }

        launchbrowser("Chrome");
        test.log(LogStatus.INFO, "Browser launch successfully");
        driver.navigate().to("https://www.myntra.com/");
        test.log(LogStatus.INFO, "Myntra page is launch successfully");

        dynamicwait(30, "Myntra_id");
        takescreenshot(test);

        s_assert.assertTrue(getelemnt("Greeting_id").toString().contains("Hello Luke"));
        test.log(LogStatus.PASS, "User logged in successfully");

        click("Createbutton_id");

        settext("FirstName_xpath", "First");
        settext("LastName_xpath", "Last");
        settext("Date_xpath", "2018-12-12");
        settext("Email_xpath", "first@gmail.com");

        click("Add_cssSelector");

        dynamicwait(30, "EmployeeList_cssSelector");
        takescreenshot(test);
        s_assert.assertTrue(getelemnt("EmployeeList_cssSelector").toString().contains("First Last"));
        test.log(LogStatus.PASS, "New Employee is created successfully");





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
        exls= new Xls_Reader(System.getProperty("user.dir")+"\\TestData\\Myntra.xlsx");

         return DataReader.getdata(exls,"TestData",TC);

    }

}
