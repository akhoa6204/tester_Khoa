package testcases.Railway;

import common.Common.Utilities;
import common.Constant.Constant;
import dataObjects.User;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.Railway.BookTicketPage;
import pageObjects.Railway.HomePage;
import pageObjects.Railway.LoginPage;
import pageObjects.Railway.TimeTablePage;

public class TimeTableTest {
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
    public void TC15(){
        System.out.println("TC15 - User can open \"Book ticket\" page by clicking on \"Book ticket\" link in \"Train timetable\" page.");

        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        User user = new User();
        loginPage.login(user);
        TimeTablePage timeTablePage= loginPage.gotoTimeTablePage();

        String departFrom = "Huế";
        String arriveAt = "Sài Gòn";
        BookTicketPage bookTicketPage = timeTablePage.selectBookTicketLink(departFrom, arriveAt);

        String actualMsg;
        String expectedMsg;

        actualMsg = Constant.WEBDRIVER.getCurrentUrl();
        expectedMsg = "BookTicketPage.cshtml";

        Assert.assertTrue(actualMsg.contains(expectedMsg),
                "URL không chứa chuỗi mong đợi: " + expectedMsg);

        actualMsg = bookTicketPage.getDepartStation();
        expectedMsg = departFrom;

        Assert.assertEquals(actualMsg, expectedMsg, "departFrom book ticket is not displayed as expected");

        actualMsg = bookTicketPage.getArriverStation();
        expectedMsg = arriveAt;

        Assert.assertEquals(actualMsg, expectedMsg, "arriveAt book ticket is not displayed as expected");
    }
}
