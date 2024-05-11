package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import static core.ConfigReader.getProperty;

public class DriverManager {
    private static WebDriver driver;

    /**
     * Retrieves the current WebDriver instance. If none exists, initializes a new driver.
     * @return The WebDriver instance to interact with the browser.
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    /**
     * Initializes the WebDriver based on the `browser` property or defaults to Chrome.
     * Handles headless and maximized modes using properties in `config.properties`.
     */
    private static void initializeDriver() {
        String browser = getProperty("browser");
        if (browser == null || browser.isBlank()) {
            browser = "chrome";
        }
        boolean headlessMode = Boolean.parseBoolean(getProperty("headless.mode"));
        boolean maximizeMode = Boolean.parseBoolean(getProperty("maximize.mode"));

        switch (browser.toLowerCase()) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--lang=es-MX");
                if (headlessMode) {
                    chromeOptions.addArguments("window-size=2560,1440");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--headless");
                }
                driver = new ChromeDriver(chromeOptions);
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headlessMode) {
                    firefoxOptions.addArguments("window-size=2560,1440");
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
            }
            case "ie", "internet explorer" -> {
                WebDriverManager.iedriver().setup();
                if (headlessMode) {
                    throw new UnsupportedOperationException("Internet Explorer does not support headless mode.");
                }
                driver = new InternetExplorerDriver(new InternetExplorerOptions());
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headlessMode) {
                    throw new UnsupportedOperationException("Edge does not support headless mode.");
                }
                driver = new EdgeDriver(edgeOptions);
            }
            default -> throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        if (maximizeMode) {
            driver.manage().window().maximize();
        }
    }

    /**
     * Quits the WebDriver instance and sets it to null to release resources.
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
