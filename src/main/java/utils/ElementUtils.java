package utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
@Slf4j
public class ElementUtils {
    private WebDriver driver;
    private WebDriverWait wait;

    private  Actions action;

    public ElementUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void waitAndClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (TimeoutException e) {
            throw new NoSuchElementException("El elemento no se ha vuelto clickeable: " + e.getMessage());
        }
    }

    public void waitAndSendKeys(WebElement element, String text) {
        try {
            WebElement webElement = wait.until(ExpectedConditions.visibilityOf(element));
            webElement.clear();
            webElement.sendKeys(text);
        } catch (TimeoutException e) {
            throw new NoSuchElementException("El elemento no es visible: " + e.getMessage());
        }
    }

    public WebElement waitAndFindBy(By locator) {
        WebElement element = null;
        for (int i = 0; i < 3; i++) { // Intentar hasta 3 veces (incluyendo el primer intento)
            try {
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                break; // Sí se encuentra el elemento, salir del bucle
            } catch (TimeoutException e) {
                log.info("Elemento no encontrado en el intento " + (i + 1));
                if (i == 2) { // Si es el último intento, lanza una excepción
                    throw new NoSuchElementException("Elemento no encontrado después de varios intentos: " + locator.toString());
                }
            }
        }
        return element;
    }

    public void waitVisibilityAndDobleClick(By locator) {
        WebElement element;
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            action.doubleClick(element);
        } catch (TimeoutException e) {
            throw new NoSuchElementException("El elemento no se ha vuelto clickeable: " + e.getMessage());
        }
    }

    public void waitAndSelectOptionFromDropdown(By locator, String option) {
        WebElement dropdown;
        try {
            dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            Select select = new Select(dropdown);
            select.selectByVisibleText(option);
        } catch (TimeoutException e) {
            throw new NoSuchElementException("El menú desplegable no se ha vuelto visible para seleccionar una opción: " + e.getMessage());
        }
    }
    public boolean waitAndVerifyText(By locator, String expectedText) {
        WebElement element;
        try {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return element.getText().equals(expectedText);
        } catch (TimeoutException e) {
            throw new NoSuchElementException("El elemento no se ha vuelto presente para verificar el texto: " + e.getMessage());
        }
    }

    public String waitAndGetText(By locator) {
        WebElement element;
        try {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return element.getText();
        } catch (TimeoutException e) {
            throw new NoSuchElementException("El elemento no se ha vuelto presente para obtener su texto: " + e.getMessage());
        }
    }
}
