package testcases.Railway;

import common.Common.Utilities;
import common.Common.WebUi;
import common.Constant.Constant;
import dataObjects.User;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageObjects.Railway.HomePage;
import pageObjects.Railway.RegisterPage;

import java.util.Random;

public class CreateAndManageAccount {
    private HomePage homePage;
    private RegisterPage registerPage;
    @AfterMethod
    public void captureScreenshot(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        WebUi.captureScreenshot(methodName);
    }
    @BeforeMethod
    public void gotoRegister(){
        homePage = new HomePage();
        homePage.open();
        registerPage = homePage.gotoRegister();
    }
    @Test
    public void TC07(){
        System.out.println("TC07 - User can create new account");

        Random rand = new Random();
        int randomNumber = 10000 + rand.nextInt(90000);
        String email = "akhoa" + randomNumber + "@gmail.com";

        User user = new User(email, "123456789", "12345678");

        String actualMsg = registerPage.register(user.getEmail(), user.getPassword(), user.getPassword(), user.getPid());
        String expectedMsg = "Thank you for registering your account";
        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
    }
    @Test
    public void TC10(){
        System.out.println("TC10 - User can't create account with \"Confirm password\" is not the same with \"Password\".");

        Random rand = new Random();
        int randomNumber = 10000 + rand.nextInt(90000);
        String email = "akhoa" + randomNumber + "@gmail.com";

        User user = new User(email, "123456780", "21842512");

        registerPage.register(user.getEmail(), user.getPassword(), "1231231212312", user.getPid());

        String actualMsg = registerPage.getMessageError();
        String expectedMsg = "There're errors in the form. Please correct the errors and try again.";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected");
    }
    @Test
    public void TC11(){
        System.out.println("TC11 - User can't create account while password and PID fields are empty");

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
