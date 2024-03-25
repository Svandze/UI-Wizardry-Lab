package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementUtils {
    private WebDriver driver;

    public ElementUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void click(WebElement element) {
        element.click();
    }

    public void sendKeys(WebElement element, String text) {
        element.sendKeys(text);
    }

    //visible present click
    //anotacion que busque por cada tipo de locator
    //enviar lista locators y interactuar con el primero
    //si es visible o esta presente que escriba en el elemento
    //un metodo padre que busque si el elemento este presente
    //verificiar el texto
    //espera hasta que el elemento sea clickeable
    // metodo para manejar alertas https://the-internet.herokuapp.com/javascript_alerts ejemplo
    // extent reporter

}
