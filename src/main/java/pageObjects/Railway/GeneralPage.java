package pageObjects.Railway;

import common.Constant.Constant;
import dataObjects.enumData.MainMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import common.Common.WebUi;
import java.time.Duration;
import java.util.List;

public class GeneralPage {
    // message
    private final By lblWelcomeMessage = By.xpath("//div[@class='account']/strong");
    private final By lblErrorMessage = By.xpath("//p[contains(@class, 'message') and contains(@class, 'error')]");

    private final String dynamicMenuItem = "//div[@id='menu']//span[contains(text(), '%s')]";

    protected WebElement getLblWelcomeMessage(){
        return WebUi.waitForElementVisible(lblWelcomeMessage, 10);
    }

    protected WebElement getLblMessageError() {
        return WebUi.waitForElementVisible(lblErrorMessage, 10);
    }


    public WebElement getDynamicMenuItem(String enumData){
        return WebUi.waitForElementVisible(By.xpath(String.format(dynamicMenuItem, enumData)), 5);
    }

    public String getWelcomeMessage(){
        return this.getLblWelcomeMessage().getText();
    }

    public String getMessageError(){
        return this.getLblMessageError().getText();
    }
    public TimeTablePage gotoTimeTablePage(){
        this.getDynamicMenuItem(MainMenu.TIME_TABLE.getValue()).click();
        return new TimeTablePage();
    }
    public BookTicketPage gotoBookTicketPage(){
        this.getDynamicMenuItem(MainMenu.BOOK_TICKET.getValue()).click();
        return new BookTicketPage();
    }
    public LoginPage gotoLoginPage(){
        this.getDynamicMenuItem(MainMenu.LOGIN.getValue()).click();
        return new LoginPage();
    }
    public MyTicketPage gotoMyTicketPage(){
        this.getDynamicMenuItem(MainMenu.MY_TICKET.getValue()).click();
        return new MyTicketPage();
    }
    public RegisterPage gotoRegister(){
        this.getDynamicMenuItem(MainMenu.REGISTER.getValue()).click();
        return new RegisterPage();
    }
    public ChangePasswordPage gotoChangePasswordPage(){
        this.getDynamicMenuItem(MainMenu.CHANGE_PASSWORD.getValue()).click();
        return new ChangePasswordPage();
    }
    public HomePage gotoLogout(){
        this.getDynamicMenuItem(MainMenu.LOGOUT.getValue()).click();
        return new HomePage();
    }

}
