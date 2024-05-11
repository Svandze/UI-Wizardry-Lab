package base;

import core.CustomPageFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import core.DriverManager;
import utils.ElementUtils;

/**
 * BaseTest is a foundational class for all test cases.
 * It manages the WebDriver instance before and after each test method.
 */
public class BaseTest {

    protected WebDriver driver;

    /**
     * Sets up the WebDriver and page elements before each test method.
     * The WebDriver is obtained from the DriverManager.
     */
    @BeforeEach
    public void beforeMethod() {
        driver = DriverManager.getDriver();
        CustomPageFactory.initElements(driver, this);
        ElementUtils.initialize(driver);
    }

    /**
     * Quits the WebDriver instance after each test method, freeing up resources.
     */
    @AfterEach
    public void afterMethod() {
        DriverManager.quitDriver();
    }
}
