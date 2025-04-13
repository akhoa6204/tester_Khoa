package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GeneralPage {
    // tab
    private final By tabLogin = By.xpath("//*[@id='menu']//a[@href='/Account/Login.cshtml']");
    private final By tabLogout= By.xpath("//*[@id='menu']//a[@href='/Account/Logout']");
    private final By tabBookTicket = By.xpath("//*[@id='menu']//a[@href=\"/Page/BookTicketPage.cshtml\"]");
    private final By tabMyTicket = By.xpath("//*[@id='menu']//a[@href=\"/Page/ManageTicket.cshtml\"]");
    private final By tabChangePassword = By.xpath("//*[@id='menu']//a[@href=\"/Account/ChangePassword.cshtml\"]");
    private final By tabRegister = By.xpath("//*[@id='menu']//a[@href=\"/Account/Register.cshtml\"]");
    private final By tabTimeTable= By.xpath("//*[@id='menu']//a[@href=\"TrainTimeListPage.cshtml\"]");
    // message
    private final By lblWelcomeMessage = By.xpath("//div[@class='account']/strong");
    private final By lblMessageError = By.xpath("//p[contains(@class, 'message') and contains(@class, 'error')]");
    private final By lblSuccessMessage = By.xpath("//p[@class=\"message success\"]");

    // ===== ADD THIS METHOD SPECIAL =====
    protected WebElement waitForElementVisible(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public List<WebElement> waitForAllElementsVisible(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void scrollIntoView(WebElement element){
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    //Elements
    // page
    protected WebElement getTabLogin(){
        return waitForElementVisible(tabLogin, 10);
    }

    protected WebElement getTabLogout(){
        return waitForElementVisible(tabLogout, 10);
    }

    protected WebElement getTabBookTicket(){
        return waitForElementVisible(tabBookTicket, 10);
    }

    protected WebElement getTabMyTicket(){
        return waitForElementVisible(tabMyTicket, 10);
    }

    protected WebElement getTabChangePassword(){
        return waitForElementVisible(tabChangePassword, 10);
    }

    protected WebElement getTabRegister(){
        return waitForElementVisible(tabRegister, 10);
    }

    protected WebElement getTimeTable() {
        return waitForElementVisible(tabTimeTable, 10);
    }
    // message
    protected WebElement getLblWelcomeMessage(){
        return waitForElementVisible(lblWelcomeMessage, 10);
    }

    protected WebElement getLblMessageError() {
        return waitForElementVisible(lblMessageError, 10);
    }

    protected WebElement getLblSuccessMessage() {
        return waitForElementVisible(lblSuccessMessage, 10);
    }

    //Method
    public TimeTablePage gotoTimeTablePage(){
        this.getTimeTable().click();
        return new TimeTablePage();
    }

    public BookTicketPage gotoBookTicketPage(){
        this.getTabBookTicket().click();
        return new BookTicketPage();
    }

    public String getMessageSuccess(){
        return this.getLblSuccessMessage().getText();
    }

    public String getWelcomeMessage(){
        return this.getLblWelcomeMessage().getText();
    }

    public LoginPage gotoLoginPage(){
        this.getTabLogin().click();
        return new LoginPage();
    }

    public String getMessageError(){
        return this.getLblMessageError().getText();
    }

    public MyTicketPage gotoMyTicketPage(){
        this.getTabMyTicket().click();
        return new MyTicketPage();
    }

    public RegisterPage gotoRegister(){
        this.getTabRegister().click();
        return new RegisterPage();
    }

    public ChangePasswordPage gotoChangePasswordPage(){
        this.getTabChangePassword().click();
        return new ChangePasswordPage();
    }

    public void clickLogout(){
        this.getTabLogout().click();
    }

}
