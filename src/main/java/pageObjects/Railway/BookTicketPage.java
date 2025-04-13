package pageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookTicketPage extends GeneralPage{
    private final By _txtDate= By.xpath("//select[@name='Date']");
    private final By _txtDepartStation = By.xpath("//select[@name='DepartStation']");
    private final By _txtArriveStation = By.xpath("//select[@name='ArriveStation']");
    private final By _txtSeatType = By.xpath("//select[@name='SeatType']");
    private final By _txtTicketAmount= By.xpath("//select[@name='TicketAmount']");
    private final By btnBookTicket = By.xpath("//input[@value=\"Book ticket\"]");
    private final By lblSuccessMessage = By.xpath("//div[@id='content']/h1");

    public WebElement getTxtDate() {
        return waitForElementVisible(_txtDate, 10);
    }

    public WebElement getTxtDepartStation() {
        return waitForElementVisible(_txtDepartStation, 10);
    }

    public WebElement getTxtArriveStation() {
        return waitForElementVisible(_txtArriveStation, 10);
    }

    public WebElement getTxtSeatType() {
        return waitForElementVisible(_txtSeatType, 10);
    }

    public WebElement getTxtTicketAmount() {
        return waitForElementVisible(_txtTicketAmount, 10);
    }

    public String getDepartStation(){
        WebElement select = this.getTxtDepartStation();
        scrollIntoView(select);
        WebElement selectedOption = select.findElement(By.xpath(".//option[1]"));
        return selectedOption.getText().trim();
    }

    public String getArriverStation(){
        WebElement select = this.getTxtArriveStation();
        scrollIntoView(select);
        WebElement selectedOption = select.findElement(By.xpath(".//option[1]"));
        return selectedOption.getText().trim();
    }

    @Override
    public WebElement getLblSuccessMessage() {
        return waitForElementVisible(lblSuccessMessage, 10);
    }

    public WebElement getBtnBookTicket() {
        return waitForElementVisible(btnBookTicket, 10);
    }

    //Methods
    public Map<String, Object> getRowData(){
        Map<String, Object> data = new HashMap<>();
        By table  = By.xpath("//table[@class=\"MyTable WideTable\"]");
        WebElement tableElement = waitForElementVisible(table, 10);

        WebElement departFromElement = tableElement.findElement(By.xpath(".//tr[2]/td[1]"));
        scrollIntoView(departFromElement);

        WebElement arriveAtElement = tableElement.findElement(By.xpath(".//tr[2]/td[2]"));
        WebElement seatTypeElement = tableElement.findElement(By.xpath(".//tr[2]/td[3]"));
        WebElement ticketAmountElement = tableElement.findElement(By.xpath(".//tr[2]/td[7]"));

        data.put("departFrom", departFromElement.getText().trim());
        data.put("arriveAt", arriveAtElement.getText().trim());
        data.put("seatType", seatTypeElement.getText().trim());
        data.put("ticketAmount", ticketAmountElement.getText().trim());

        return data;
    }

    public void bookTicket(Map<String, Object> data){
        scrollIntoView(this.getTxtDepartStation());
        this.getTxtDepartStation().click();
        By optionDepartStation = By.xpath("//select[@name='DepartStation']/option[text()='" + data.get("departFrom") + "']");
        WebElement optionElement = waitForElementVisible(optionDepartStation, 10);
        scrollIntoView(optionElement);
        optionElement.click();

        scrollIntoView(this.getTxtArriveStation());
        this.getTxtArriveStation().click();
        By optionArriveStation = By.xpath("//select[@name='ArriveStation']/option[text()='" + data.get("arriveAt") + "']");
        WebElement optionArriveElement = waitForElementVisible(optionArriveStation, 10);
        scrollIntoView(optionArriveElement);
        optionArriveElement.click();

        scrollIntoView(this.getTxtSeatType());
        this.getTxtSeatType().click();
        By optionSeatType = By.xpath("//select[@name='SeatType']/option[text()='" + data.get("seatType") + "']");
        WebElement optionSeatElement = waitForElementVisible(optionSeatType, 10);
        scrollIntoView(optionSeatElement);
        optionSeatElement.click();

        scrollIntoView(this.getTxtTicketAmount());
        this.getTxtTicketAmount().click();
        By optionTicketAmount = By.xpath("//select[@name='TicketAmount']/option[text()='" + data.get("ticketAmount") + "']");
        WebElement optionTicketElement = waitForElementVisible(optionTicketAmount, 10);
        scrollIntoView(optionTicketElement);
        optionTicketElement.click();

        scrollIntoView(this.getBtnBookTicket());
        this.getBtnBookTicket().click();
    }

}
