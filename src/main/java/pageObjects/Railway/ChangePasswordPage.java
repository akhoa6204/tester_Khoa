package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class ChangePasswordPage extends GeneralPage{
    private final By _txtCurrentPassword = By.xpath("//input[@id='currentPassword']");
    private final By _txtNewPassword = By.xpath("//input[@id='newPassword']");
    private final By _txtConfirmedPassword = By.xpath("//input[@id='confirmPassword']");
    private final By btnChangePassword = By.xpath("//input[@value='Change Password']");
    protected WebElement getTxtCurrentPassword() {
        return waitForElementVisible(_txtCurrentPassword, 10);
    }

    protected WebElement getTxtNewPassword() {
        return waitForElementVisible(_txtNewPassword, 10);
    }

    protected WebElement getTxtConfirmedPassword() {
        return waitForElementVisible(_txtConfirmedPassword, 10);
    }

    protected WebElement getBtnChangePassword() {
        return waitForElementVisible(btnChangePassword, 10);
    }


    public String changePassword(String currentPassword, String newPassword){
        this.getTxtCurrentPassword().sendKeys(currentPassword);
        this.getTxtNewPassword().sendKeys(newPassword);

        scrollIntoView(this.getBtnChangePassword());

        getBtnChangePassword().click();
        return this.getMessageSuccess();
    }

}
