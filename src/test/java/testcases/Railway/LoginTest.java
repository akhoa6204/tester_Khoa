package testcases.Railway;

import common.Common.Utilities;
import common.Constant.Constant;
import dataObjects.Ticket;
import dataObjects.User;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.internal.YamlParser;
import pageObjects.Railway.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoginTest {
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
    public void TC01() {
        System.out.println("TC01 - User can log into Railway with valid username and password.");
        HomePage homePage = new HomePage();
        homePage.open();
        User user = new User();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(user);

        String actualMsg = loginPage.getWelcomeMessage();
        String expectedMsg = "Welcome " + Constant.USERNAME;

        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected.");
    }
    @Test
    public void TC02(){
        System.out.println("TC02 - User can't login with blank \"Username\" textbox.");
        HomePage homePage = new HomePage();
        homePage.open();
        User user = new User("", Constant.PASSWORD);
        LoginPage loginPage = homePage.gotoLoginPage();

        loginPage.login(user);
        String actualMsg = loginPage.getMessageError();
        String expectedMsg = "There was a problem with your login and/or errors exist in your form.";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected.");
    }
    @Test
    public void TC03(){
        System.out.println("TC03 - User cannot log into Railway with invalid password");
        HomePage homePage = new HomePage();
        homePage.open();

        User user = new User("khoa@gmail.com", "asasasdasd");

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(user);

        String actualMsg = loginPage.getMessageError();
        String expectedMsg = "There was a problem with your login and/or errors exist in your form.";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected.");
    }
    @Test
    public void TC04(){
        System.out.println("TC04 - Login page displays when un-logged User clicks on \"Book ticket\" tab.");
        HomePage homePage = new HomePage();
        homePage.open();
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();

        String actualMsg = Constant.WEBDRIVER.getCurrentUrl();
        String expectedMsg = "http://railwayb1.somee.com/Account/Login.cshtml?ReturnUrl=/Page/BookTicketPage.cshtml";

        Assert.assertEquals(actualMsg, expectedMsg, "Login page is not displayed as expected.");
    }
    @Test
    public void TC05(){
        System.out.println("TC05 - System shows message when user enters wrong password several times.");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        User user = new User(Constant.USERNAME, "errorPassword");
        for(int i = 0; i < 3; i++){
            loginPage.login(user);
            loginPage.clearUsernameAndPassword();
        }

        String actualMsg = loginPage.getMessageError();
        String expectedMsg = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected.");

    }
    @Test
    public void TC06(){
        System.out.println("TC06 - Additional pages display once user logged in.");
        HomePage homePage = new HomePage();
        homePage.open();
        User user = new User();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(user);

        MyTicketPage myTicketPage = loginPage.gotoMyTicketPage();
        String actualMsg = Constant.WEBDRIVER.getCurrentUrl();
        String expectedMsg = "http://railwayb1.somee.com/Page/ManageTicket.cshtml";

        Assert.assertEquals(actualMsg, expectedMsg, "ManageTicket is not displayed as expected.");

        ChangePasswordPage changePasswordPage = myTicketPage.gotoChangePasswordPage();
        actualMsg = Constant.WEBDRIVER.getCurrentUrl();
        expectedMsg = "http://railwayb1.somee.com/Account/ChangePassword.cshtml";

        Assert.assertEquals(actualMsg, expectedMsg, "ChangePassword is not displayed as expected.");

    }
    @Test
    public void TC08(){
        System.out.println("TC08 - User can't login with an account hasn't been activated.");

        HomePage homePage = new HomePage();
        homePage.open();
        User user = new User("as@gmail.com", "asdasasdqw");
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(user);
        String actualMsg = loginPage.getMessageError();
        String expectedMsg = "Invalid username or password. Please try again.";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected");
    }
}
