package testcases.Railway;

import common.Common.Utilities;
import common.Constant.Constant;
import dataObjects.Ticket;
import dataObjects.User;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.Railway.BookTicketPage;
import pageObjects.Railway.HomePage;
import pageObjects.Railway.LoginPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookTicketTest {
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
    public void TC14(){
        System.out.println("TC14 - User can book 1 ticket at a time.");

        HomePage homePage = new HomePage();
        homePage.open();
        User user =new User();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(user);

        BookTicketPage bookTicketPage = loginPage.gotoBookTicketPage();

        LocalDate date = LocalDate.now().plusDays(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String formattedDate = date.format(formatter);

        Ticket actualTicket = new Ticket(formattedDate, "Sài Gòn", "Nha Trang", "Hard seat", "1");
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
}
