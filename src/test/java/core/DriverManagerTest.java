package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class DriverManagerTest {

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        mockStatic(WebDriverManager.class);
        when(WebDriverManager.chromedriver()).thenReturn(null); //
        when(ConfigReader.getProperty("browser")).thenReturn("chrome");
        when(ConfigReader.getProperty("headless.mode")).thenReturn("true");
        when(ConfigReader.getProperty("maximize.mode")).thenReturn("true");
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }

    @Test
    public void testGetDriverSingletonBehavior() {
        WebDriver driver1 = DriverManager.getDriver();
        WebDriver driver2 = DriverManager.getDriver();
        assertSame(driver1, driver2);
        driver1.quit();
        driver2.quit();
    }
}