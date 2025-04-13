package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage  extends GeneralPage{
    private final By _txtUsername = By.xpath("//*[@id=\"username\"]");
    private final By _txtPassword = By.xpath("//*[@id=\"password\"]");
    private final By _btnLogin = By.xpath("//input[@value='login']");
    // Elements
    public WebElement getTxtUsername(){
        return waitForElementVisible(_txtUsername, 10);
    }
    public WebElement getTxtPassword(){
        return waitForElementVisible(_txtPassword, 10);
    }
    public WebElement getBtnLogin(){
        return waitForElementVisible(_btnLogin, 10);
    }
    public void clearUsernameAndPassword(){
        this.getTxtUsername().clear();
        this.getTxtPassword().clear();
    }
    //Methods
    public HomePage login(String username, String password){
        this.getTxtUsername().sendKeys(username);
        this.getTxtPassword().sendKeys(password);
        scrollIntoView(this.getBtnLogin());
        this.getBtnLogin().click();

        //Land on Home page
        return new HomePage();
    }
}
