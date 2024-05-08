package base;

import core.CustomPageFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import core.DriverManager;
import utils.ElementUtils;


public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void beforeMethod() {
        driver = DriverManager.getDriver();
        CustomPageFactory.initElements(driver, this);
        ElementUtils.initialize(driver);
    }

    @AfterEach
    public void afterMethod() {
        DriverManager.quitDriver();
    }
}
