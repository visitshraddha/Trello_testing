package com.trello.basetest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest {

    public WebDriver driver = null;
    public ExtentReports report = null;
    public ExtentTest test = null;
    public Properties prop = null;
    public Xls_Reader xls = null;
    public SoftAssert s_assert = null;

    public void loadconfig() {
        if (prop == null) {
            try {
                FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\config.properties");
                prop = new Properties();
                prop.load(fis);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void launchbrowser(String BrowserType) {
        try {
            if (driver == null) {
                if ("IE".equals(BrowserType)) {
                    System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\resources\\IEDriverServer.exe");
                    driver = new InternetExplorerDriver();


                } else if ("Chrome".equals(BrowserType)) {
                    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\resources\\chromedriver.exe");
                    driver = new ChromeDriver();


                } else if ("FF".equals(BrowserType)) {
                    System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\resources\\geckodriver.exe");
                    driver = new FirefoxDriver();
                }
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public WebElement getelemnt(String locator) {

        if (locator.endsWith("xpath")) {
            return driver.findElement(By.xpath(prop.getProperty(locator)));
        } else if (locator.endsWith("id")) {
            return driver.findElement(By.id(prop.getProperty(locator)));
        } else if (locator.endsWith("name")) {
            return driver.findElement(By.name(prop.getProperty(locator)));
        } else if (locator.endsWith("class")) {
            return driver.findElement(By.className(prop.getProperty(locator)));
        } else if (locator.endsWith("cssSelector")) {
            return driver.findElement(By.cssSelector(prop.getProperty(locator)));
        } else if (locator.endsWith("linktest")) {
            return driver.findElement(By.linkText(prop.getProperty(locator)));
        } else {
            test.log(LogStatus.ERROR, "Incorrect locator");
        }

        return null;

    }

    public void settext(String locator, String inputtext) {
        WebElement elem = getelemnt(locator);
        elem.sendKeys(inputtext);

    }

    public void click(String locator) {
        WebElement elem = getelemnt(locator);
        elem.click();
    }


    public void takescreenshot(ExtentTest Test) {
        Date d = new Date();
        String TimeStamp = d.toString().replaceAll(" ", "_").replace(":", "_");
        File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {

            FileUtils.copyFile(f,
                    new File(System.getProperty("user.dir") + "\\screenshots\\" + "Screenshot_" + TimeStamp + ".PNG"));
            test.log(LogStatus.INFO, test.addBase64ScreenShot(
                    System.getProperty("user.dir") + "\\screenshots\\" + "Screenshot_" + TimeStamp + ".PNG"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void dynamicwait(int waittime, String locator) {
        WebDriverWait wait = new WebDriverWait(driver, waittime);
        if (locator.endsWith("xpath")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty(locator))));
        } else if (locator.endsWith("class")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(prop.getProperty(locator))));
        } else if (locator.endsWith("cssSelector")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(prop.getProperty(locator))));
        } else if (locator.endsWith("linktest")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(prop.getProperty(locator))));
        } else if (locator.endsWith("name")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(prop.getProperty(locator))));
        } else if (locator.endsWith("id")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(prop.getProperty(locator))));
        }
    }
}



