package utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

@Slf4j
public class ElementUtils {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Actions action;

    public static void initialize(WebDriver webDriver) {
        driver = webDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        action = new Actions(driver);
    }

    public static void waitAndClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (TimeoutException e) {
            log.error("El elemento no se ha vuelto clickeable: " + e.getMessage());
            throw new NoSuchElementException("El elemento no se ha vuelto clickeable: " + e.getMessage());
        }
    }

    public static void waitAndSendKeys(WebElement element, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(element));

            wait.until((WebDriver d) -> {
                Point initialLocation = element.getLocation();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                Point finalLocation = element.getLocation();
                return initialLocation.equals(finalLocation);
            });

            element.clear();
            element.sendKeys(text);
        } catch (TimeoutException e) {
            log.error("El elemento no es interactuable: " + e.getMessage());
            throw new NoSuchElementException("El elemento no es interactuable: " + e.getMessage());
        }
    }

    public static void waitVisibilityAndDoubleClick(WebElement element) {
        try {
            WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
            action.doubleClick(visibleElement).perform();
        } catch (TimeoutException e) {
            log.error("El elemento no se ha vuelto visible para doble clic: " + e.getMessage());
            throw new NoSuchElementException("El elemento no se ha vuelto visible para doble clic: " + e.getMessage());
        }
    }

    public static void waitAndSelectOptionFromDropdown(WebElement dropdown, String option) {
        try {
            WebElement visibleDropdown = wait.until(ExpectedConditions.visibilityOf(dropdown));
            Select select = new Select(visibleDropdown);
            select.selectByVisibleText(option);
        } catch (TimeoutException e) {
            log.error("El menú desplegable no se ha vuelto visible para seleccionar una opción: " + e.getMessage());
            throw new NoSuchElementException("El menú desplegable no se ha vuelto visible para seleccionar una opción: " + e.getMessage());
        }
    }

    public static boolean waitAndVerifyText(WebElement element, String expectedText) {
        try {
            WebElement presentElement = wait.until(ExpectedConditions.visibilityOf(element));
            return presentElement.getText().equals(expectedText);
        } catch (TimeoutException e) {
            log.error("El elemento no se ha vuelto presente para verificar el texto: " + e.getMessage());
            throw new NoSuchElementException("El elemento no se ha vuelto presente para verificar el texto: " + e.getMessage());
        }
    }

    public static String waitAndGetText(WebElement element) {
        try {
            WebElement presentElement = wait.until(ExpectedConditions.visibilityOf(element));
            return presentElement.getText();
        } catch (TimeoutException e) {
            log.error("El elemento no se ha vuelto presente para obtener su texto: " + e.getMessage());
            throw new NoSuchElementException("El elemento no se ha vuelto presente para obtener su texto: " + e.getMessage());
        }
    }

    public static void windowHandler(String targetWindowTitle) {
        Set<String> windowHandles = driver.getWindowHandles();
        boolean foundWindow = false;
        for (String windowHandle : windowHandles) {
            driver.switchTo().window(windowHandle);
            if (driver.getTitle().equals(targetWindowTitle)) {
                foundWindow = true;
                break;
            }
        }
        if (!foundWindow) {
            throw new RuntimeException("No se encontró la ventana con el título: " + targetWindowTitle);
        }
    }

    public static void clickWithJavaScript(WebElement element) {
        implicitWait();
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        implicitWait();
    }

    public static void implicitWait() {
        long startTime = System.currentTimeMillis();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(1)).until(webDriver -> System.currentTimeMillis() - startTime > 1000);
        } catch (Exception e) {
            log.error("Error durante la espera implícita", e);
        }
    }
}
