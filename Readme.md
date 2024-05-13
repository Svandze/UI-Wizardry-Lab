# UI-Wizardry-Lab

Esta es una libreria de automatización en Java utilizando Selenium con el patrón POM (Page Object Model), diseñada para
pruebas
automatizadas de interfaces de usuario. La automatización se centra en la ejecución de flujos E2E.

## Prerrequisitos

- [x] Java JDK 17 o superior
- [x] Apache Maven 3.6 o superior para compilar y gestionar las dependencias del proyecto

## Instalación

Para instalar este proyecto en tu máquina local, sigue estos pasos:

1. Clona el repositorio en tu máquina local usando:
    ```sh
    git clone https://github.com/tu_usuario/UI-Wizardry-Lab.git
    ```
2. Navega hasta el directorio del proyecto usando:
    ```sh
    cd UI-Wizardry-Lab
    ```

## Ejecución de las pruebas

Para ejecutar las pruebas, usa el siguiente comando en la raíz del proyecto:

```bash
mvn clean verify
```

## Configuración

El archivo config.properties se utiliza para configurar el navegador utilizado en las pruebas. Por defecto, está
configurado para usar Google Chrome. Puedes indicar qué navegador utilizar (edge, firefox, ie), si deseas ejecuciones
headless (sin interfaz gráfica) y si deseas configurar las ejecuciones a pantalla completa.

```markdown
browser=chrome
headless.mode=false
maximize.mode=false
```

## Inclusión de la librería en proyectos de automatización

Actualmente, la versión estable de la librería es la 1.0.0. Esta versión debe ser incluida en el proyecto de
automatización para poder hacer uso de los métodos de la dependencia.

```xml

<dependencies>
    <dependency>
        <groupId>co.com.enequipo.fw</groupId>
        <artifactId>enequipo</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>

```

Una vez instalada la dependencia en nuestro nuevo proyecto, se recomienda ejecutar el comando
ConfigManager.initProject() en nuestra clase principal:

```java
public class Main {
    public static void main(String[] args) {
        ConfigManager.initProject();
    }
}

```

Esto inicializará nuestro proyecto y creará la estructura base con la que podremos trabajar.

## Ejemplo de PageObject generado

```java
package pages;

import base.BasePage;
import core.annotations.FindByList;
import core.annotations.FindByList.Locator;
import org.openqa.selenium.WebElement;

import static core.annotations.FindByList.LocatorType.ID;
import static core.annotations.FindByList.LocatorType.CSS;
import static core.annotations.FindByList.LocatorType.XPATH;
import static utils.ElementUtils.waitAndClick;
import static utils.ElementUtils.waitAndSendKeys;

/**
 * La clase `HomePage` representa la página principal en tu framework de automatización.
 * Esta clase hereda de `BasePage` para reutilizar funcionalidades básicas de página.
 *
 * Los elementos web se inicializan dinámicamente usando el patrón Page Factory, permitiendo
 * un diseño modular del Page Object Model. Todas las clases de página deben extender `HomePage`
 * o `BasePage` para mantener la consistencia.
 * @see BasePage
 */
public class HomePage extends BasePage {

    /**
     * La anotación `FindByList` permite especificar múltiples localizadores para un solo elemento web.
     * La prioridad se basa en la eficiencia del localizador: ID > Name > ClassName > CSS > XPath.
     * Esta anotación es útil para elementos dinámicos.
     * @see FindBy
     * @see FindByList
     */
    @FindByList({
            @Locator(type = ID, value = "user-name"),
            @Locator(type = CSS, value = "#user-name"),
            @Locator(type = XPATH, value = "//input[@placeholder='Username']")
    })
    public WebElement usernameInput;

    @FindBy(id = "password")
    public WebElement passwordInput;

    @FindBy(id = "login-button")
    public WebElement loginButton;

    /**
     * Abre la página principal navegando a su URL usando la instancia de WebDriver.
     */
    public void openPage() {
        driver.get("https://www.saucedemo.com/");
    }

    /**
     * Inicia sesión en la aplicación ingresando las credenciales y haciendo clic en el botón de inicio de sesión.
     * Los métodos utilitarios de `ElementUtils` se utilizan para la interacción con los elementos.
     * @see utils.ElementUtils
     */
    public void doLogin() {
        waitAndSendKeys(usernameInput, "standard_user");
        waitAndSendKeys(passwordInput, "secret_sauce");
        waitAndClick(loginButton);
    }
}

```

## Ejemplo de test generado

```java
package steps;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.HomePage;

/**
 * La clase `ExampleTest` demuestra cómo implementar un caso de prueba usando el framework de automatización.
 * Al extender `BaseTest`, el ciclo de vida de WebDriver se gestiona automáticamente.
 *
 * Las clases de prueba deben centrarse en abstracciones y afirmaciones, mientras que las clases de página deben
 * manejar acciones específicas de la página y localizadores. Esta modularidad garantiza una estructura mantenible
 * y organizada del Page Object Model.
 * @see BaseTest
 */
public class ExampleTest extends BaseTest {

    /**
     * El método `exampleTest` realiza un caso de prueba básico para iniciar sesión en la página principal.
     * Inicializa una instancia de `HomePage` y llama a sus métodos para navegar e iniciar sesión.
     * Las afirmaciones verifican que la URL de la página resultante coincide con la esperada.
     */
    @Test
    public void exampleTest() {
        HomePage homePage = new HomePage();
        homePage.openPage();
        homePage.doLogin();
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
    }
}


```

Ambas clases proporcionan un ejemplo básico de cómo estructurar tus pruebas. Se sigue el patrón Page Object y la gestión
del ciclo de vida de WebDriver es manejada a nivel de framework, por lo que solo necesitas enfocarte en escribir los
scripts sin preocuparte por la gestión del WebDriver.Ambas clases proporcionan un ejemplo básico de cómo estructurar tus
pruebas. Se sigue el patrón Page Object y la gestión del ciclo de vida de WebDriver es manejada a nivel de framework,
por lo que solo necesitas enfocarte en escribir los scripts sin preocuparte por la gestión del WebDriver.

## Uso de la Anotación @FindByList

La anotación @FindByList permite especificar múltiples localizadores para un solo elemento. Esto es útil en casos donde
un elemento puede tener diferentes localizadores en distintas versiones de una página o en diferentes contextos.

```java
import org.openqa.selenium.WebElement;
import core.annotations.FindByList;

import static core.annotations.FindByList.LocatorType.ID;
import static core.annotations.FindByList.LocatorType.CSS;
import static core.annotations.FindByList.LocatorType.XPATH;

public class SamplePage {

    @FindByList({
            @Locator(type = ID, value = "user-name"),
            @Locator(type = CSS, value = "#user-name"),
            @Locator(type = XPATH, value = "//input[@placeholder='Username']")
    })
    private WebElement dynamicElement;

    // Métodos de la clase
}

```

La inclusión de esta anotación simplifica enormemente el trabajo al generar nuestros localizadores. Esta anotación
prioriza la búsqueda por localizadores de tipo ID, luego Name, y así sucesivamente, priorizando los localizadores que
son buscados de manera más eficiente por los navegadores.