package pageObjects.Railway;

import common.Common.WebUi;
import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class RegisterPage extends GeneralPage{
    private final By _txtEmail = By.xpath("//*[@id=\"email\"]");
    private final By _txtPassword = By.xpath("//*[@id=\"password\"]");
    private final By _txtConfirmedPassword = By.xpath("//*[@id=\"confirmPassword\"]");
    private final By _txtPassport = By.xpath("//*[@id=\"pid\"]");
    private final By btnRegister = By.xpath("//input[@value='Register']");
    private final By lblWelcomeMessage = By.xpath("//div[@id='content']/p");

    private final By lblErrorMessageForPassword = By.xpath("//label[@for=\"password\" and @class=\"validation-error\"]");
    private final By lblErrorMessageForPid = By.xpath("//label[@for=\"pid\" and @class=\"validation-error\"]");

    public WebElement getTxtEmail() {
        return WebUi.waitForElementVisible(_txtEmail, 10);
    }

    public WebElement getTxtPassword() {
        return WebUi.waitForElementVisible(_txtPassword, 10);
    }

    public WebElement getTxtConfirmedPassword() {
        return WebUi.waitForElementVisible(_txtConfirmedPassword, 10);
    }

    public WebElement getTxtPassport() {
        return WebUi.waitForElementVisible(_txtPassport, 10);
    }

    public WebElement getBtnRegister() {
        return WebUi.waitForElementVisible(btnRegister, 10);
    }

    public WebElement getLblWelcomeMessage(){
        return WebUi.waitForElementVisible(lblWelcomeMessage, 10);
    }
    public WebElement getLblErrorMessageForPassword(){
        return WebUi.waitForElementVisible(lblErrorMessageForPassword, 10);
    }public WebElement getLblErrorMessageForPid(){
        return WebUi.waitForElementVisible(lblErrorMessageForPid, 10);
    }
    //Methods
    public String getErrorMessageForPassword(){
        return getLblErrorMessageForPassword().getText();
    }

    public String getErrorMessageForPid(){
        return getLblErrorMessageForPid().getText();
    }
    public String register(String email, String password, String confirmedPassword, String pid){
        this.getTxtEmail().sendKeys(email);
        this.getTxtPassword().sendKeys(password);
        this.getTxtConfirmedPassword().sendKeys(confirmedPassword);
        this.getTxtPassport().sendKeys(pid);

        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", this.getBtnRegister());
        this.getBtnRegister().click();

        return this.getLblWelcomeMessage().getText();
    }
}
