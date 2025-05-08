package pageObjects.Railway;

import common.Common.WebUi;
import common.Constant.Constant;
import dataObjects.User;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage  extends GeneralPage{
    private final String dynamicInput = "//input[@id='%s']";
    private final By _btnLogin = By.xpath("//input[@value='login']");

    private By getDynamicInput(String text){
        return By.xpath(String.format(dynamicInput, text));
    }
    public WebElement getTxtUsername(){
        return WebUi.waitForElementVisible(getDynamicInput("username"), 10);
    }
    public WebElement getTxtPassword(){
        return WebUi.waitForElementVisible(getDynamicInput("password"), 10);
    }
    public WebElement getBtnLogin(){
        return WebUi.waitForElementVisible(_btnLogin, 10);
    }
    public void clearUsernameAndPassword(){
        this.getTxtUsername().clear();
        this.getTxtPassword().clear();
    }
    //Methods
    public void login(User user){
        this.getTxtUsername().sendKeys(user.getEmail());
        this.getTxtPassword().sendKeys(user.getPassword());
        WebUi.scrollIntoView(this.getBtnLogin());
        this.getBtnLogin().click();
    }
}
