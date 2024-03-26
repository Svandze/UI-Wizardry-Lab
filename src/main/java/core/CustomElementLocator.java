package core;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import java.util.List;

public class CustomElementLocator implements ElementLocator {
    private final WebDriver driver;
    private final List<By> bys;

    public CustomElementLocator(WebDriver driver, List<By> bys) {
        this.driver = driver;
        this.bys = bys;
    }

    @Override
    public WebElement findElement() {
        for (By by : bys) {
            try {
                WebElement element = driver.findElement(by);
                if (element != null) {
                    return element;
                }
            } catch (NoSuchElementException e) {

            }
        }
        throw new NoSuchElementException("No se pudo encontrar el elemento con los selectores proporcionados.");
    }

    @Override
    public List<WebElement> findElements() {
        throw new UnsupportedOperationException("CustomElementLocator solo soporta findElement.");
    }
}
