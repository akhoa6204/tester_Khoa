package common.Common;

import common.Constant.Constant;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class WebUi {
    public static WebElement waitForElementVisible(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void scrollIntoView(WebElement element){
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public static void captureScreenshot(String nameTest) {
        try {
            if (Constant.WEBDRIVER != null) {
                File screenshot = ((TakesScreenshot) Constant.WEBDRIVER).getScreenshotAs(OutputType.FILE);

                String screenshotPath = "D:/screenshots/" + nameTest + ".png";
                File destination = new File(screenshotPath);

                if (!destination.getParentFile().exists()) {
                    destination.getParentFile().mkdirs();
                }

                Files.copy(screenshot.toPath(), destination.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
