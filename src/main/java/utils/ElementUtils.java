package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementUtils {
    private WebDriver driver;

    public ElementUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void click(WebElement element) {
        element.click();
    }

    public void sendKeys(WebElement element, String text) {
        element.sendKeys(text);
    }
}
