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

public class Board_Tc extends BaseTest {
    public String TC="Create_TC";
    public Xls_Reader exls=null;


    @Test(dataProvider="tcdata")
    public void Board_Tc(Hashtable<String, String>ht){
        report=ExtentManager.getInstance();
        test=report.startTest("Board_Tc"+ ht.toString());
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

        dynamicwait(10, "Board_xpath");
        click("Board_xpath");

        dynamicwait(10, "AddCard_xpath");
        click("AddCard_xpath");
        dynamicwait(10, "card_xpath");
        settext("card_xpath",ht.get("ThingsToDo"));
        click("thingstodoclose_xpath");
        test.log(LogStatus.PASS, "Card details added successfully in ToDo list");
        takescreenshot(test);
        //Move to Doing phase
        click("Movelist_xpath");
        click("MoveOption_xpath");
        click("MoveToDoing_xpath");
        dynamicwait(10,"Doinglist_xpath");

        test.log(LogStatus.PASS, "Card Moved successfully into Doing list");
        takescreenshot(test);

        dynamicwait(10,"Doinglist_xpath");
        click("Doinglist_xpath");
        click("MoveOption_xpath");
        click("DoingToDone_xpath");
        dynamicwait(10,"Donelist_xpath");

        test.log(LogStatus.PASS, "Card Moved successfully into Done list");
        takescreenshot(test);


        click("Donelist_xpath");
        click("Archive_xpath");
        click("archivebutton_xpath");

        dynamicwait(10,"Donelist_xpath");

        test.log(LogStatus.PASS, "Card successfully archieved from Done list");
        takescreenshot(test);

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
