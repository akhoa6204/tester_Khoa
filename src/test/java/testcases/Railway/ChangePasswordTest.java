package testcases.Railway;

import common.Common.Utilities;
import common.Constant.Constant;
import dataObjects.User;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.Railway.ChangePasswordPage;
import pageObjects.Railway.HomePage;
import pageObjects.Railway.LoginPage;

public class ChangePasswordTest {
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
    public void TC09(){
        System.out.println("TC09 - User can change password.");

        HomePage homePage = new HomePage();
        homePage.open();
        User user = new User();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(user);
        ChangePasswordPage changePasswordPage= loginPage.gotoChangePasswordPage();
        String actualMsg = changePasswordPage.changePassword(Constant.PASSWORD, Constant.NEW_PASSWORD);
        String expectedMsg = "Your password has been updated!";
        Assert.assertEquals(actualMsg, expectedMsg, "Success message is not displayed as expected");
    }
}
