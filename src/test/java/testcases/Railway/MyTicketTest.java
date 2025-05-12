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
import pageObjects.Railway.MyTicketPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyTicketTest {
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
    public void TC16(){
        System.out.println("TC16 - User can cancel a ticket");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        User user = new User();
        loginPage.login(user);

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
