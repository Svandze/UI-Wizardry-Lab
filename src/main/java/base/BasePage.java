package base;

import core.CustomPageFactory;
import core.DriverManager;
import org.openqa.selenium.WebDriver;

/**
 * BasePage is a foundational class that other page classes should extend.
 * It provides the WebDriver instance to interact with web elements and
 * initializes them using the CustomPageFactory.
 */
public class BasePage {

    protected WebDriver driver;

    /**
     * Constructor that accepts an existing WebDriver instance.
     * Useful when a specific WebDriver instance is passed externally.
     *
     * @param driver The WebDriver instance to use.
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Default constructor that initializes the WebDriver instance by
     * obtaining it from the DriverManager and initializes page elements
     * using the CustomPageFactory.
     */
    public BasePage() {
        this.driver = DriverManager.getDriver();
        CustomPageFactory.initElements(driver, this);
    }
}
