package utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class ElementUtils {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Actions action;

    // Este método debe ser llamado una vez al inicializar la clase/utilidad
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
            WebElement webElement = wait.until(ExpectedConditions.visibilityOf(element));
            webElement.clear();
            webElement.sendKeys(text);
        } catch (TimeoutException e) {
            log.error("El elemento no es visible: " + e.getMessage());
            throw new NoSuchElementException("El elemento no es visible: " + e.getMessage());
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
}
