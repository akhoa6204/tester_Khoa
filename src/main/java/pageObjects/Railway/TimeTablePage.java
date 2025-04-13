package pageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeTablePage extends GeneralPage {
    private final By rowByList = By.xpath("//table[@class='MyTable WideTable']//tr");
    private final Map<String, Map<String, List<Map<String, Object>>>> tableData = new HashMap<>();

    public TimeTablePage() {
        this.getRowDataList();
    }

    protected List<WebElement> getRowByList() {
        return waitForAllElementsVisible(rowByList, 10);
    }

    public void getRowDataList() {
        List<WebElement> rows = this.getRowByList();

        for (int i = 1; i < rows.size(); i++) {
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));

            String departFrom = cells.get(1).getText().trim();
            String arriveAt = cells.get(2).getText().trim();
            String departTime = cells.get(3).getText().trim();
            String arriveTime = cells.get(4).getText().trim();

            WebElement btnCheckPrice, btnBookTicket;
            try {
                btnCheckPrice = cells.get(5).findElement(By.tagName("a"));
            } catch (Exception e) {
                btnCheckPrice = null;
            }

            try {
                btnBookTicket = cells.get(6).findElement(By.tagName("a"));
            } catch (Exception e) {
                btnBookTicket = null;
            }

            // Tạo thông tin chuyến đi
            Map<String, Object> trip = new HashMap<>();
            trip.put("Depart Time", departTime);
            trip.put("Arrive Time", arriveTime);
            trip.put("Check Price", btnCheckPrice);
            trip.put("Book ticket", btnBookTicket);

            // Cập nhật vào tableData
            // Map<departFrom, Map<arriveAt, List<trips>>>
            tableData
                    .computeIfAbsent(departFrom, k -> new HashMap<>())
                    .computeIfAbsent(arriveAt, k -> new ArrayList<>())
                    .add(trip);
        }
    }

    public BookTicketPage clickBtnBookTicket(String departFrom, String arriveAt, String departTime) {
        List<Map<String, Object>> listData = tableData.get(departFrom).get(arriveAt);
        boolean check = false;
        for(int i = 0; i< listData.size(); i++){
            if (String.valueOf(listData.get(i).get("Depart Time")).equals(departTime)){
                try {
                    WebElement btnBookTicket = (WebElement) listData.get(i).get("Book ticket");
                    if (btnBookTicket != null) {
                        scrollIntoView(btnBookTicket);
                        btnBookTicket.click();
                        check = true;
                    } else {
                        throw new RuntimeException("Không tìm thấy nút 'Book ticket' cho chuyến đi " + departFrom + " -> " + arriveAt + " lúc " + departTime);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Lỗi khi click vào nút 'Book ticket': " + e.getMessage(), e);
                }
            }
        }

        return check ? gotoBookTicketPage() : null;
    }

    // Cấu trúc lưu dữ liệu
    //    "Sài Gòn": {
    //        "Phan Thiết": [
    //                {
    //                    "Depart Time": "7:30",
    //                    "Arrive Time": "11:30",
    //                    "Check Price": "check price",
    //                    "Book ticket": "link to book"
    //                },
    //                {
    //                     "Depart Time": "7:30",
    //                     "Arrive Time": "11:30",
    //                     "Check Price": "check price",
    //                     "Book ticket": "link to book"
    //                }
    //            ],
    //        "Đà Nẵng": [
    //                {
    //                    "Depart Time": "7:30",
    //                    "Arrive Time": "11:30",
    //                    "Check Price": "check price",
    //                    "Book ticket": "link to book"
    //                },
    //                {
    //                     "Depart Time": "7:30",
    //                     "Arrive Time": "11:30",
    //                     "Check Price": "check price",
    //                     "Book ticket": "link to book"
    //                }
    //            ],
    //       }
    //}
}
