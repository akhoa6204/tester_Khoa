package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTicketPage extends GeneralPage{
    private final By table = By.xpath("//table[@class='MyTable']");
    private final By rowByList = By.xpath("//table[@class='MyTable']//tr");
    private final Map<String, Map<String, Object>> tableData = new HashMap<>();

    public MyTicketPage(){
        this.getRowDataList();
    }
    protected List<WebElement> getRowByList() {
        return waitForAllElementsVisible(rowByList, 10);
    }
    protected WebElement getTable(){
        return waitForElementVisible(table, 10);
    }
    protected boolean checkRow(String idTicket){
        try{
            this.getTable();
        }catch (Exception e){
            return false;
        }
        List<WebElement> rows = this.getRowByList();
        if (rows.size() < 2) return false;
        for (int i = 1; i < rows.size(); i++){
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
            WebElement cell = cells.get(cells.size() - 1);
            WebElement btnCancel = cell.findElement(By.xpath("./input"));

            String onclickAttr = btnCancel.getAttribute("onclick");
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(onclickAttr);
            String id= "";
            if (matcher.find()) {
                id = matcher.group();
            }
            if (idTicket.equals(id)) return true;
        }
        return  false;
    }

    public void getRowDataList() {
        List<WebElement> rows = this.getRowByList();

        for (int i = 1; i < rows.size(); i++) {
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));

            String departFrom = cells.get(1).getText().trim();
            String arriveAt = cells.get(2).getText().trim();
            String seatType = cells.get(3).getText().trim();
            String departDate = cells.get(4).getText().trim();
            String bookDate = cells.get(5).getText().trim();
            String expiredDate = cells.get(6).getText().trim();
            String status = cells.get(7).getText().trim();
            String amount = cells.get(8).getText().trim();
            String totalPrice = cells.get(9).getText().trim();
            WebElement btnCancel = cells.get(10).findElement(By.xpath("./input[@value='Cancel']"));

            String onclickAttr = btnCancel.getAttribute("onclick");
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(onclickAttr);
            String idTicket = "";
            if (matcher.find()) {
                idTicket = matcher.group();
            }

            Map<String, Object> trip = new HashMap<>();
            trip.put("Depart Station", departFrom);
            trip.put("Arive Station", arriveAt);
            trip.put("Seat Type", seatType);
            trip.put("Depart Date", departDate);
            trip.put("Book Date", bookDate);
            trip.put("Expired Date", expiredDate);
            trip.put("Status", status);
            trip.put("Amount", amount);
            trip.put("Total Price", totalPrice);
            trip.put("Operation", btnCancel);


            tableData.put(idTicket, trip);
        }
    }
    public boolean cancelTicket(String idTicket){
        WebElement btnCancel = (WebElement) tableData.get(idTicket).get("Operation");
        scrollIntoView(btnCancel);
        btnCancel.click();
        Alert alert = Constant.WEBDRIVER.switchTo().alert();
        alert.accept();

        return checkRow(idTicket);

    }
}
