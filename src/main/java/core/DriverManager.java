package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

public class DriverManager extends ConfigReader {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        String browser = getProperty("browser");
        boolean headlessMode = Boolean.parseBoolean(getProperty("headless.mode"));
        boolean maximizeMode = Boolean.parseBoolean(getProperty("maximize.mode"));

        if (driver == null) {
            if ("chrome".equalsIgnoreCase(browser)) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headlessMode) {
                    options.addArguments("--headless");
                }
                driver = new ChromeDriver(options);
            } else if ("firefox".equalsIgnoreCase(browser)) {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                if (headlessMode) {
                    options.addArguments("--headless");
                }
                driver = new FirefoxDriver(options);
            } else if ("ie".equalsIgnoreCase(browser) || "internet explorer".equalsIgnoreCase(browser)) {
                WebDriverManager.iedriver().setup();
                InternetExplorerOptions options = new InternetExplorerOptions();
                if (headlessMode) {
                    throw new UnsupportedOperationException("Internet Explorer does not support headless mode.");
                }
                driver = new InternetExplorerDriver(options);
            } else if ("edge".equalsIgnoreCase(browser)) {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                if (headlessMode) {
                    options.addArguments("--headless");
                }
                driver = new EdgeDriver(options);
            } else {
                throw new IllegalArgumentException("Browser not supported: " + browser);
            }
        }

        if (maximizeMode) {
            driver.manage().window().maximize();
        }

        return driver;
    }
}
