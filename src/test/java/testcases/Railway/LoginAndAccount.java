package testcases.Railway;

import common.Common.WebUi;
import common.Constant.Constant;
import dataObjects.User;
import dataObjects.enumData.MainMenu;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.Railway.*;

public class LoginAndAccount {
    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void goToLogin(){
        homePage = new HomePage();
        loginPage = homePage.gotoLoginPage();
    }
    @AfterMethod
    public void captureScreenshot(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        WebUi.captureScreenshot(methodName);
        try{
            homePage = homePage.gotoLogout();
        }catch (Exception e){

        }
    }
    @Test
    public void TC01() {
        System.out.println("TC01 - User can log into Railway with valid username and password.");

        User user = new User();
        loginPage.login(user);

        String actualMsg = loginPage.getWelcomeMessage();
        String expectedMsg = "Welcome " + Constant.USERNAME;

        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected.");
    }
    @Test
    public void TC02(){
        System.out.println("TC02 - User can't login with blank \"Username\" textbox.");

        User user = new User("", Constant.PASSWORD);

        loginPage.login(user);
        String actualMsg = loginPage.getMessageError();
        String expectedMsg = "There was a problem with your login and/or errors exist in your form.";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected.");
    }
    @Test
    public void TC03(){
        System.out.println("TC03 - User cannot log into Railway with invalid password");

        User user = new User("khoa@gmail.com", "asasasdasd");

        loginPage.login(user);

        String actualMsg = loginPage.getMessageError();
        String expectedMsg = "There was a problem with your login and/or errors exist in your form.";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected.");
    }
    @Test
    public void TC04(){
        System.out.println("TC04 - Login page displays when un-logged User clicks on \"Book ticket\" tab.");

        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();

        String actualMsg = Constant.WEBDRIVER.getCurrentUrl();
        String expectedMsg = "http://railwayb1.somee.com/Account/Login.cshtml?ReturnUrl=/Page/BookTicketPage.cshtml";

        Assert.assertEquals(actualMsg, expectedMsg, "Login page is not displayed as expected.");
    }
    @Test
    public void TC05(){
        System.out.println("TC05 - System shows message when user enters wrong password several times.");

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

        User user = new User();

        loginPage.login(user);

        loginPage.getDynamicMenuItem(MainMenu.MY_TICKET.getValue());
        loginPage.getDynamicMenuItem(MainMenu.CHANGE_PASSWORD.getValue());
    }
    @Test
    public void TC08(){
        System.out.println("TC08 - User can't login with an account hasn't been activated.");

        User user = new User("as@gmail.com", "asdasasdqw");

        loginPage.login(user);

        String actualMsg = loginPage.getMessageError();
        String expectedMsg = "Invalid username or password. Please try again.";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected");
    }
    @Test
    public void TC09(){
        System.out.println("TC09 - User can change password.");

        User user = new User();

        loginPage.login(user);

        ChangePasswordPage changePasswordPage= loginPage.gotoChangePasswordPage();
        String actualMsg = changePasswordPage.changePassword(Constant.PASSWORD, Constant.NEW_PASSWORD);
        String expectedMsg = "Your password has been updated!";

        Assert.assertEquals(actualMsg, expectedMsg, "Success message is not displayed as expected");
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
