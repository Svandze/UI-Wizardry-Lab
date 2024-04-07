package base;

import com.github.javafaker.Faker;
import core.CustomPageFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import core.DriverManager;
import utils.ElementUtils;

import javax.swing.text.Element;


public class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public static void setUp() {
    }

    @AfterClass
    public static void tearDown() {
    }

    @Before
    public void beforeMethod() {
        driver = DriverManager.getDriver();
        CustomPageFactory.initElements(driver,this);
        ElementUtils.initialize(driver);
    }

    @After
    public void afterMethod() {
        if (driver != null) {
            driver.quit();
        }
    }
}
