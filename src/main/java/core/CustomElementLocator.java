package core;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

/**
 * CustomElementLocator is responsible for locating web elements using a prioritized list of locators.
 * It attempts to find an element by each locator in the provided list and returns the first successful match.
 */
@Slf4j
public class CustomElementLocator implements ElementLocator {
    private final WebDriver driver;
    private final List<By> bys;

    /**
     * Constructor for CustomElementLocator.
     *
     * @param driver The WebDriver instance to use for locating elements.
     * @param bys A list of By locators to use for locating web elements.
     */
    public CustomElementLocator(WebDriver driver, List<By> bys) {
        this.driver = driver;
        this.bys = bys;
    }

    /**
     * Tries to find a web element using the prioritized list of By locators.
     * Returns the first found element, or throws a NoSuchElementException if none are found.
     *
     * @return The first successfully located web element.
     * @throws NoSuchElementException If no element is found using any of the By locators.
     */
    @Override
    public WebElement findElement() {
        for (By by : bys) {
            try {
                WebElement element = driver.findElement(by);
                if (element != null) {
                    return element;
                }
            } catch (NoSuchElementException e) {
                log.error("No element with such locators is present.");
            }
        }
        throw new NoSuchElementException("Could not find the element using the provided locators.");
    }

    @Override
    public List<WebElement> findElements() {
        throw new UnsupportedOperationException("CustomElementLocator only supports findElement.");
    }
}
