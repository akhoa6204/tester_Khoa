package pageObjects.Railway;

import common.Common.WebUi;
import common.Constant.Constant;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;


public class MyTicketPage extends GeneralPage{
    String dynamicCancelTicket = "//input[contains(@onclick, 'DeleteTicket(%d)')]";
    private By getDynamicCancelTicket(Long idTicket){
        return By.xpath(String.format(dynamicCancelTicket, idTicket));
    }
    public WebElement getCancelTicket(Long idTicket){
        return WebUi.waitForElementVisible(this.getDynamicCancelTicket(idTicket), 10);
    }
    public void selectCancelTicket(Long idTicket){
        WebUi.scrollIntoView(this.getCancelTicket(idTicket));
        this.getCancelTicket(idTicket).click();
        Alert alert = Constant.WEBDRIVER.switchTo().alert();
        alert.accept();
    }
    public boolean isTicketCancelled(Long idTicket){
        try {
            this.getCancelTicket(idTicket);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
