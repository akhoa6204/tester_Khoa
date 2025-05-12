package pageObjects.Railway;

import common.Common.WebUi;
import common.Constant.Constant;
import dataObjects.Ticket;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookTicketPage extends GeneralPage{
    private final String dynamicSelect = "//select[@name='%s']";
    private final String dynamicColumn = "//tr[2]/td[position() = count(//th[contains(normalize-space(text()), \"%s\")]/preceding-sibling::th) + 1]";
    private final By btnBookTicket = By.xpath("//input[@value=\"Book ticket\"]");
    private final By lblSuccessMessage = By.xpath("//div[@id='content']/h1");

    public By getDynamicSelect(String text) {
        return By.xpath(String.format(dynamicSelect, text));
    }
    public By getDynamicColumn(String text){
        return By.xpath(String.format(dynamicColumn, text));
    }
    public WebElement getTxtDate() {
        return WebUi.waitForElementVisible(getDynamicSelect("Date"), 10);
    }

    public WebElement getTxtDepartStation() {
        return WebUi.waitForElementVisible(getDynamicSelect("DepartStation"), 10);
    }

    public WebElement getTxtArriveStation() {
        return WebUi.waitForElementVisible(getDynamicSelect("ArriveStation"), 10);
    }

    public WebElement getTxtSeatType() {
        return WebUi.waitForElementVisible(getDynamicSelect("SeatType"), 10);
    }

    public WebElement getTxtTicketAmount() {
        return WebUi.waitForElementVisible(getDynamicSelect("TicketAmount"), 10);
    }

    public String getDepartStation(){
        WebElement select = this.getTxtDepartStation();
        WebUi.scrollIntoView(select);
        WebElement selectedOption = select.findElement(By.xpath(".//option[1]"));
        return selectedOption.getText().trim();
    }

    public String getArriverStation(){
        WebElement select = this.getTxtArriveStation();
        WebUi.scrollIntoView(select);
        WebElement selectedOption = select.findElement(By.xpath(".//option[1]"));
        return selectedOption.getText().trim();
    }

    public WebElement getLblSuccessMessage() {
        return WebUi.waitForElementVisible(lblSuccessMessage, 10);
    }

    public WebElement getBtnBookTicket() {
        return WebUi.waitForElementVisible(btnBookTicket, 10);
    }

    public String getTextSuccessMessage(){
        return this.getLblSuccessMessage().getText();
    }
    //Methods
    public void selectFromDropdown(WebElement dropdown, String optionText) {
        WebUi.scrollIntoView(dropdown);
        dropdown.click();

        String option = ".//option[text()='%s']";
        WebElement optionElement = dropdown.findElement(By.xpath(String.format(option, optionText)));
        WebUi.scrollIntoView(optionElement);
        optionElement.click();

    }

    public String bookTicket(Ticket ticket){
        selectFromDropdown(this.getTxtDate(), ticket.getDate());
        selectFromDropdown(this.getTxtDepartStation(), ticket.getDepart());
        selectFromDropdown(this.getTxtDepartStation(), ticket.getDepart());
        selectFromDropdown(this.getTxtArriveStation(), ticket.getArrive());
        selectFromDropdown(this.getTxtSeatType(), ticket.getType());
        selectFromDropdown(this.getTxtTicketAmount(), ticket.getAmount());

        WebUi.scrollIntoView(this.getBtnBookTicket());
        this.getBtnBookTicket().click();
        return Constant.WEBDRIVER.getCurrentUrl().split("\\?id=")[1];
    }

    public Ticket getDataTicket(){
        Ticket ticket = new Ticket();
        ticket.setDate(WebUi.waitForElementVisible(this.getDynamicColumn("Depart Date"), 10).getText());
        ticket.setDepart(WebUi.waitForElementVisible(this.getDynamicColumn("Depart Station"), 10).getText());
        ticket.setArrive(WebUi.waitForElementVisible(this.getDynamicColumn("Arrive Station"), 10).getText());
        ticket.setType(WebUi.waitForElementVisible(this.getDynamicColumn("Seat Type"), 10).getText());
        ticket.setAmount(WebUi.waitForElementVisible(this.getDynamicColumn("Amount"), 10).getText());

        return ticket;
    }
}
