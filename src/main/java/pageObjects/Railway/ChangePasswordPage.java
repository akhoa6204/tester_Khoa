package pageObjects.Railway;

import common.Common.WebUi;
import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class ChangePasswordPage extends GeneralPage{
    private final By btnChangePassword = By.xpath("//input[@value='Change Password']");
    private final By txtMessageSuccess = By.xpath("//p[@class='message success']");
    private String dynamicInput = "//input[@id='%s']";

    private By getDynamicInput(String text){
        return By.xpath(String.format(dynamicInput, text));
    }
    protected WebElement getTxtCurrentPassword() {
        return WebUi.waitForElementVisible(getDynamicInput("currentPassword"), 10);
    }
    protected String getMessageSuccess(){
        return WebUi.waitForElementVisible(txtMessageSuccess, 5).getText();
    }
    protected WebElement getTxtNewPassword() {
        return WebUi.waitForElementVisible(getDynamicInput("newPassword"), 10);
    }
    protected WebElement getTxtConfirmedPassword() {
        return WebUi.waitForElementVisible(getDynamicInput("confirmPassword"), 10);
    }
    protected WebElement getBtnChangePassword() {
        return WebUi.waitForElementVisible(btnChangePassword, 10);
    }
    public String changePassword(String currentPassword, String newPassword){
        this.getTxtCurrentPassword().sendKeys(currentPassword);
        this.getTxtNewPassword().sendKeys(newPassword);
        this.getTxtConfirmedPassword().sendKeys(newPassword);

        WebUi.scrollIntoView(this.getBtnChangePassword());

        getBtnChangePassword().click();
        return this.getMessageSuccess();
    }

}
