package testcases.Railway;

import common.Common.Utilities;
import common.Constant.Constant;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ForgotPasswordTest {
    @BeforeTest
    public void beforeMethod(){
        System.out.println("Pre-condition");
        System.setProperty("Webdriver.chrome.driver", Utilities.getProjectPath() + "\\Executables\\chromedriver.exe");
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
    }
    @AfterClass
    public void afterMethod(){
        System.out.println("Post-condition");
        Constant.WEBDRIVER.quit();
    }
    @Test
    public void TC12(){
        System.out.println("TC12 - Errors display when password reset token is blank.");
    }
    @Test
    public void TC13(){
        System.out.println("TC13 - Errors display if password and confirm password don't match when resetting password.");
    }
}
