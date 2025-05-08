package testcases.Railway;

import common.Common.Utilities;
import common.Constant.Constant;
import dataObjects.User;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.Railway.HomePage;
import pageObjects.Railway.RegisterPage;

import java.util.Random;

public class RegisterTest {
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
    public void TC07(){
        System.out.println("TC07 - User can create new account");

        HomePage homePage = new HomePage();
        homePage.open();
        Random rand = new Random();
        int randomNumber = 10000 + rand.nextInt(90000);
        String email = "akhoa" + randomNumber + "@gmail.com";
        User user = new User(email, "123456789", "12345678");
        RegisterPage registerPage = homePage.gotoRegister();
        String actualMsg = registerPage.register(user.getEmail(), user.getPassword(), user.getPassword(), user.getPid());
        String expectedMsg = "Thank you for registering your account";
        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
    }
    @Test
    public void TC10(){
        System.out.println("TC10 - User can't create account with \"Confirm password\" is not the same with \"Password\".");

        HomePage homePage = new HomePage();
        homePage.open();
        RegisterPage registerPage = homePage.gotoRegister();
        Random rand = new Random();
        int randomNumber = 10000 + rand.nextInt(90000);
        String email = "akhoa" + randomNumber + "@gmail.com";
        User user = new User(email, "123456780", "2184512");
        registerPage.register(user.getEmail(), user.getPassword(), user.getPassword(), user.getPid());

        String actualMsg = registerPage.getMessageError();
        String expectedMsg = "There're errors in the form. Please correct the errors and try again.";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected");
    }
    @Test
    public void TC11(){
        System.out.println("TC11 - User can't create account while password and PID fields are empty");

        HomePage homePage = new HomePage();
        homePage.open();
        RegisterPage registerPage = homePage.gotoRegister();
        User user = new User("sad@gmail.com" , "", "");
        registerPage.register(user.getEmail(), user.getPassword(), user.getPassword(), user.getPid());

        String actualMsg = registerPage.getMessageError();
        String expectedMsg = "There're errors in the form. Please correct the errors and try again.";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected.");

        actualMsg = registerPage.getErrorMessageForPassword();
        expectedMsg = "Invalid password length";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message for password is not displayed as expected.");

        actualMsg = registerPage.getErrorMessageForPid();
        expectedMsg = "Invalid ID length";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message for ID is not displayed as expected.");
    }
}
