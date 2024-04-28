package base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class BasePageTest {
    private BasePage basePage;
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = mock(WebDriver.class);
        basePage = new BasePage(driver);
    }

    @Test
    public void testDriverInitialization() {
        assertNotNull(basePage.driver, "Driver should not be null");
    }
}