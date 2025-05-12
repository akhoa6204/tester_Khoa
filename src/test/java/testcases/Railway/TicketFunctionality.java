package testcases.Railway;

import common.Common.Utilities;
import common.Common.WebUi;
import common.Constant.Constant;
import dataObjects.Ticket;
import dataObjects.User;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageObjects.Railway.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TicketFunctionality {
    private HomePage homePage;
    private LoginPage loginPage;
    private  User user;
    @BeforeClass
    public void setUp(){
        homePage = new HomePage();
        homePage.open();
        user =new User();
        loginPage = homePage.gotoLoginPage();
        loginPage.login(user);
    }
    @AfterClass
    public void tearDown(){
        homePage.gotoLogout();
    }
    @AfterMethod
    public void captureScreenshot(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        WebUi.captureScreenshot(methodName);
    }
    @Test
    public void TC14() throws InterruptedException{
        System.out.println("TC14 - User can book 1 ticket at a time.");

        BookTicketPage bookTicketPage = loginPage.gotoBookTicketPage();

        LocalDate date = LocalDate.now().plusDays(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String formattedDate = date.format(formatter);
        Ticket actualTicket = new Ticket(formattedDate, "Đà Nẵng", "Nha Trang", "Hard seat", "1");
        String idTicket = bookTicketPage.bookTicket(actualTicket);
        actualTicket.setId(idTicket);
        Ticket expectedTicket = bookTicketPage.getDataTicket();
        String actualMessageMsg = bookTicketPage.getTextSuccessMessage();
        String expectedMessageMsg = "Ticket booked successfully!";

        Assert.assertEquals(actualMessageMsg, expectedMessageMsg, "Success message is not displayed as expected.");
        Assert.assertEquals(actualTicket.getDate(), expectedTicket.getDate(), "Depart date mismatch");
        Assert.assertEquals(actualTicket.getDepart(), expectedTicket.getDepart(), "Depart station mismatch");
        Assert.assertEquals(actualTicket.getArrive(), expectedTicket.getArrive(), "Arrive station mismatch");
        Assert.assertEquals(actualTicket.getType(), expectedTicket.getType(), "Seat type mismatch");
        Assert.assertEquals(actualTicket.getAmount(), expectedTicket.getAmount(), "Ticket amount mismatch");
    }
    @Test
    public void TC15(){
        System.out.println("TC15 - User can open \"Book ticket\" page by clicking on \"Book ticket\" link in \"Train timetable\" page.");

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
    @Test
    public void TC16()  throws InterruptedException{
        System.out.println("TC16 - User can cancel a ticket");

        BookTicketPage bookTicketPage = loginPage.gotoBookTicketPage();

        LocalDate date = LocalDate.now().plusDays(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String formattedDate = date.format(formatter);

        Ticket actualTicket = new Ticket(formattedDate, "Đà Nẵng", "Nha Trang", "Hard seat", "1");

        actualTicket.setId(bookTicketPage.bookTicket(actualTicket));
        MyTicketPage myTicketPage = bookTicketPage.gotoMyTicketPage();

        myTicketPage.selectCancelTicket(Long.parseLong(actualTicket.getId()));
        boolean actualMsg = myTicketPage.isTicketCancelled(Long.parseLong(actualTicket.getId()));
        boolean expectedMsg = false;

        Assert.assertEquals(actualMsg, expectedMsg, "Cancel ticket is error");
    }
}
