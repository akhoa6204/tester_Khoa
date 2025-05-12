package pageObjects.Railway;

import common.Common.WebUi;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


public class TimeTablePage extends GeneralPage {
    String dynamicBookTicketLink = "//tr[td[2][contains(text(), '%s')] and td[3][contains(text(), '%s')]]//a[contains(@href, 'BookTicketPage')]";
    String row = "//tr[td[2][contains(text(), '%s')] and td[3][contains(text(), '%s')]]";
    public WebElement getBookTicketLink(String departStation, String arriveStation){
        return WebUi.waitForElementVisible(By.xpath(String.format(dynamicBookTicketLink, departStation, arriveStation)), 3);
    }
    public WebElement getRow(String departStation, String arriveStation){
        return WebUi.waitForElementVisible(By.xpath(String.format(row, departStation, arriveStation)), 3);
    }
    public BookTicketPage selectBookTicketLink(String departStation, String arriveStation){
        try{
            WebUi.scrollIntoView(this.getBookTicketLink(departStation, arriveStation));
            this.getBookTicketLink(departStation, arriveStation).click();
            return new BookTicketPage();
        }
        catch (Exception e){
            WebUi.scrollIntoView(this.getRow(departStation, arriveStation));
            throw new RuntimeException("Không tìm thấy liên kết cho ga đi: " + departStation + " và ga đến: " + arriveStation);
        }
    }
}

