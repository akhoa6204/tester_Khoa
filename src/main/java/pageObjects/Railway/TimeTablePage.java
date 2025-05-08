package pageObjects.Railway;

import common.Common.WebUi;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class TimeTablePage extends GeneralPage {
    String dynamicBookTicketLink = "//tr[td[2][contains(text(), '%s')] and td[3][contains(text(), '%s')]]//a[contains(@href, 'BookTicketPage')]";
    public WebElement getBookTicketLink(String departStation, String arriveStation){
        return WebUi.waitForElementVisible(By.xpath(String.format(dynamicBookTicketLink, departStation, arriveStation)), 10);
    }
    public BookTicketPage selectBookTicketLink(String departStation, String arriveStation){
        WebUi.scrollIntoView(this.getBookTicketLink(departStation, arriveStation));
        this.getBookTicketLink(departStation, arriveStation).click();
        return new BookTicketPage();
    }
}

