package testcases.Railway;

import common.Common.Utilities;
import common.Constant.Constant;
import org.openqa.selenium.Alert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.Railway.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoginTest {
    @BeforeTest
    public void beforeMethod(){
        System.out.println("Pre-condition");

        System.setProperty("Webdriver.chrome.driver", Utilities.getProjectPath() + "\\Executables\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();

        // Thêm các flags để tắt mọi cảnh báo về mật khẩu
        options.addArguments(
                "--disable-features=PasswordLeakDetection,PasswordCheck,EnablePasswordChangeSupport",
                "--disable-save-password-bubble",
                "--no-sandbox",
                "--disable-notifications",
                "--disable-popup-blocking",
                "--disable-extensions",
                "--disable-background-networking",
                "--disable-default-apps"
        );

        // Tắt trình quản lý mật khẩu qua prefs
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.default_content_setting_values.notifications", 2); // Tắt thông báo
        options.setExperimentalOption("prefs", prefs);

        // Khởi tạo WebDriver
        Constant.WEBDRIVER = new ChromeDriver(options);
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

        LoginPage loginPage = homePage.gotoLoginPage();

        String actualMsg = loginPage.login(Constant.USERNAME, Constant.PASSWORD).getWelcomeMessage();
        String expectedMsg = "Welcome " + Constant.USERNAME;

        System.out.println(actualMsg);
        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected.");
    }
    @Test
    public void TC02(){
        System.out.println("TC02 - User can't login with blank \"Username\" textbox.");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();

        loginPage.login("", Constant.PASSWORD);
        String actualMsg = loginPage.getMessageError();
        String expectedMsg = "There was a problem with your login and/or errors exist in your form.";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected.");
    }
    @Test
    public void TC03(){
        System.out.println("TC03 - User cannot log into Railway with invalid password");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();

        loginPage.login("khoa@gmail.com", "asasasdasd");
        String actualMsg = loginPage.getMessageError();
        String expectedMsg = "There was a problem with your login and/or errors exist in your form.";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected.");
    }
    @Test
    public void TC04(){
        System.out.println("TC04 - Login page displays when un-logged User clicks on \"Book ticket\" tab.");
        HomePage homePage = new HomePage();
        homePage.open();
        homePage.clickBookTicket();

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

        for(int i = 0; i < 3; i++){
            loginPage.login(Constant.USERNAME, "errorPassword");
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
        try {
            homePage.clickLogout();
        } catch (Exception e) {
            // do nothing
        }
        LoginPage loginPage = homePage.gotoLoginPage();
        HomePage currentHomePage = loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        currentHomePage.gotoMyTicketPage();
        String actualMsg = Constant.WEBDRIVER.getCurrentUrl();
        String expectedMsg = "http://railwayb1.somee.com/Page/ManageTicket.cshtml";

        Assert.assertEquals(actualMsg, expectedMsg, "ManageTicket is not displayed as expected.");

        currentHomePage.gotoChangePasswordPage();
        actualMsg = Constant.WEBDRIVER.getCurrentUrl();
        expectedMsg = "http://railwayb1.somee.com/Account/ChangePassword.cshtml";
        Assert.assertEquals(actualMsg, expectedMsg, "ChangePassword is not displayed as expected.");

    }
    @Test
    public void TC07(){
        System.out.println("TC07 - User can create new account");

        HomePage homePage = new HomePage();
        homePage.open();
        Random rand = new Random();
        int randomNumber = 10000 + rand.nextInt(90000);
        String email = "akhoa" + randomNumber + "@gmail.com";
        RegisterPage registerPage = homePage.gotoRegister();
        String actualMsg = registerPage.register(email, "123456789", "123456789", "12345678");
        String expectedMsg = "Thank you for registering your account";
        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");

    }
    @Test
    public void TC08(){
        System.out.println("TC08 - User can't login with an account hasn't been activated.");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login("as@gmail.com", "asdasasdqw");
        String actualMsg = loginPage.getMessageError();
        String expectedMsg = "Invalid username or password. Please try again.";

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected");
    }
    @Test
    public void TC09(){
        System.out.println("TC09 - User can change password.");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        ChangePasswordPage changePasswordPage= loginPage.gotoChangePasswordPage();
        String actualMsg = changePasswordPage.changePassword(Constant.PASSWORD, Constant.NEW_PASSWORD);
        String expectedMsg = "Your password has been updated";

        Assert.assertEquals(actualMsg, expectedMsg, "Success message is not displayed as expected");
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
        registerPage.register(email, "123456789", "123456780", "2184512");

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
        registerPage.register("sad@gmail.com","", "", "");

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
    @Test
    public void TC12(){
        System.out.println("TC12 - Errors display when password reset token is blank.");
    }
    @Test
    public void TC13(){
        System.out.println("TC13 - Errors display if password and confirm password don't match when resetting password.");
    }
    @Test
    public void TC14(){
        System.out.println("TC14 - User can book 1 ticket at a time.");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        BookTicketPage bookTicketPage = loginPage.gotoBookTicketPage();
        Map<String, Object> actualTableMsg = new HashMap<>();
        actualTableMsg.put("departFrom", "Sài Gòn");
        actualTableMsg.put("arriveAt", "Nha Trang");
        actualTableMsg.put("seatType", "Soft bed with air conditioner");
        actualTableMsg.put("ticketAmount", "1");

        bookTicketPage.bookTicket(actualTableMsg);
        Map<String, Object> expectedTableMsg = bookTicketPage.getRowData();

        String actualMessageMsg = bookTicketPage.getMessageSuccess();
        String expectedMessageMsg = "Ticket booked successfully!";

        Assert.assertEquals(actualMessageMsg, expectedMessageMsg, "Success message is not displayed as expected.");

        for (String key : actualTableMsg.keySet()) {
            Assert.assertEquals(actualTableMsg.get(key), expectedTableMsg.get(key), "Mismatch at key: " + key);
        }
    }
    @Test
    public void TC15(){
        System.out.println("TC15 - User can open \"Book ticket\" page by clicking on \"Book ticket\" link in \"Train timetable\" page.");

        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        TimeTablePage timeTablePage= loginPage.gotoTimeTablePage();

        String departFrom = "Huế";
        String arriveAt = "Sài Gòn";
        String departTime= "7:30";
        BookTicketPage bookTicketPage = timeTablePage.clickBtnBookTicket(departFrom, arriveAt, departTime);

        String actualMsg;
        String expectedMsg;
        actualMsg = bookTicketPage.getDepartStation();
        expectedMsg = departFrom;

        Assert.assertEquals(actualMsg, expectedMsg, "departFrom book ticket is not displayed as expected");

        actualMsg = bookTicketPage.getArriverStation();
        expectedMsg = arriveAt;

        Assert.assertEquals(actualMsg, expectedMsg, "arriveAt book ticket is not displayed as expected");
    }
    @Test
    public void TC16() throws InterruptedException{
        System.out.println("TC16 - User can cancel a ticket");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        BookTicketPage bookTicketPage = loginPage.gotoBookTicketPage();
        Thread.sleep(5000);
        Map<String, Object> infoNewTicket = new HashMap<>();
        infoNewTicket.put("departFrom", "Quảng Ngãi");
        infoNewTicket.put("arriveAt", "Phan Thiết");
        infoNewTicket.put("seatType", "Soft bed with air conditioner");
        infoNewTicket.put("ticketAmount", "1");

        bookTicketPage.bookTicket(infoNewTicket);
        String idTicket = Constant.WEBDRIVER.getCurrentUrl().split("\\?id=")[1];
        MyTicketPage myTicketPage = bookTicketPage.gotoMyTicketPage();

        boolean actualMsg = myTicketPage.cancelTicket(idTicket);
        boolean expectedMsg = false;

        Assert.assertEquals(actualMsg, expectedMsg, "Cancel ticket is error");
    }
}
