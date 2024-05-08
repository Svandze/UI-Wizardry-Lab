//package core;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.openqa.selenium.WebDriver;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mockStatic;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class DriverManagerTest {
//
//    @BeforeEach
//    public void setUp() {
//        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
//        mockStatic(WebDriverManager.class);
//        when(WebDriverManager.chromedriver()).thenReturn(null); //
//        when(ConfigReader.getProperty("browser")).thenReturn("chrome");
//        when(ConfigReader.getProperty("headless.mode")).thenReturn("true");
//        when(ConfigReader.getProperty("maximize.mode")).thenReturn("true");
//    }
//
//    @AfterEach
//    public void tearDown() {
//        DriverManager.quitDriver();
//    }
//
//    @Test
//    public void testGetDriverSingletonBehavior() {
//        WebDriver driver1 = DriverManager.getDriver();
//        WebDriver driver2 = DriverManager.getDriver();
//        assertSame(driver1, driver2);
//        driver1.quit();
//        driver2.quit();
//    }
//}