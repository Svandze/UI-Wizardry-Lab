package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverManager extends ConfigReader {
    private static WebDriver driver;
    public static WebDriver getDriver() {
        String browser = getProperty("browser");
        if (driver == null) {
            if ("chrome".equalsIgnoreCase(browser)) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else if ("firefox".equalsIgnoreCase(browser)) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            } else if ("ie".equalsIgnoreCase(browser) || "internet explorer".equalsIgnoreCase(browser)) {
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
            } else if ("edge".equalsIgnoreCase(browser)) {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            } else {
                throw new IllegalArgumentException("Browser not supported: " + browser);
            }
        }
        return driver;
    }
}
