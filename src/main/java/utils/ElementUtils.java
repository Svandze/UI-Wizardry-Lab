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

    /**
     * Initializes the utility class with a WebDriver instance.
     * This setup is essential for performing WebDriver-based actions.
     *
     * @param webDriver The WebDriver instance used for automation tasks.
     */
    public static void initialize(WebDriver webDriver) {
        driver = webDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        action = new Actions(driver);
    }


    /**
     * Waits until the given WebElement is clickable and then clicks it.
     *
     * @param element The WebElement to be clicked.
     * @throws NoSuchElementException If the element is not clickable within the timeout.
     */
    public static void waitAndClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (TimeoutException e) {
            log.error("El elemento no se ha vuelto clickeable: " + e.getMessage());
            throw new NoSuchElementException("El elemento no se ha vuelto clickeable: " + e.getMessage());
        }
    }

    /**
     * Waits until the given WebElement is clickable, and then sends the specified text.
     * It also ensures the element remains stationary during the interaction.
     *
     * @param element The WebElement to receive the input text.
     * @param text The text to send to the WebElement.
     * @throws NoSuchElementException If the element is not clickable within the timeout.
     */
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

    /**
     * Waits until the WebElement is visible and double-clicks it using an `Actions` object.
     *
     * @param element The WebElement to double-click.
     * @throws NoSuchElementException If the element is not visible within the timeout.
     */
    public static void waitVisibilityAndDoubleClick(WebElement element) {
        try {
            WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
            action.doubleClick(visibleElement).perform();
        } catch (TimeoutException e) {
            log.error("El elemento no se ha vuelto visible para doble clic: " + e.getMessage());
            throw new NoSuchElementException("El elemento no se ha vuelto visible para doble clic: " + e.getMessage());
        }
    }

    /**
     * Waits until a dropdown WebElement becomes visible, then selects an option by its visible text.
     *
     * @param dropdown The dropdown WebElement containing various options.
     * @param option The visible text of the option to select from the dropdown.
     * @throws NoSuchElementException If the dropdown or the desired option is not visible within the timeout.
     */
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

    /**
     * Waits until a WebElement is visible and then verifies if its text matches the expected value.
     *
     * @param element The WebElement containing the text to verify.
     * @param expectedText The expected text value to compare with the WebElement's text.
     * @return True if the actual text matches the expected text; otherwise, false.
     * @throws NoSuchElementException If the WebElement is not visible within the timeout.
     */
    public static boolean waitAndVerifyText(WebElement element, String expectedText) {
        try {
            WebElement presentElement = wait.until(ExpectedConditions.visibilityOf(element));
            return presentElement.getText().equals(expectedText);
        } catch (TimeoutException e) {
            log.error("El elemento no se ha vuelto presente para verificar el texto: " + e.getMessage());
            throw new NoSuchElementException("El elemento no se ha vuelto presente para verificar el texto: " + e.getMessage());
        }
    }

    /**
     * Waits until a WebElement becomes visible and retrieves its text content.
     *
     * @param element The WebElement containing the text content to retrieve.
     * @return The text content of the WebElement.
     * @throws NoSuchElementException If the WebElement is not visible within the timeout.
     */
    public static String waitAndGetText(WebElement element) {
        try {
            WebElement presentElement = wait.until(ExpectedConditions.visibilityOf(element));
            return presentElement.getText();
        } catch (TimeoutException e) {
            log.error("El elemento no se ha vuelto presente para obtener su texto: " + e.getMessage());
            throw new NoSuchElementException("El elemento no se ha vuelto presente para obtener su texto: " + e.getMessage());
        }
    }

    /**
     * Switches to the browser window with the specified title.
     *
     * @param targetWindowTitle The title of the target window to switch to.
     * @throws RuntimeException If no window with the specified title is found among the open windows.
     */
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

    /**
     * Clicks on a WebElement using JavaScript to bypass conventional Selenium interactions.
     * This method is useful for elements that may be hidden behind other UI components.
     *
     * @param element The WebElement to click using JavaScript.
     */
    public static void clickWithJavaScript(WebElement element) {
        implicitWait();
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    /**
     * Scrolls the page to bring the specified WebElement into view.
     *
     * @param element The WebElement to scroll to.
     */
    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        implicitWait();
    }

    /**
     * Pauses the execution for a short duration to allow for UI updates.
     * It uses a custom WebDriverWait to wait until a specific amount of time has passed.
     */
    public static void implicitWait() {
        long startTime = System.currentTimeMillis();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(webDriver -> System.currentTimeMillis() - startTime > 1000);
        } catch (Exception e) {
            log.error("Error durante la espera implícita", e);
        }
    }
}
