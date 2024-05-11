package core;

import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

@Slf4j
public class ConfigManager {
    /**
     * Initializes the project structure by creating the `pages` and `steps` directories
     * under `test/java/`, and then generates the HomePage and ExampleTest classes.
     */
    public static void initProject() {
        String basePath = "src/test/java";
        String pagesPackage = "pages";
        String stepsPackage = "steps";

        try {
            createBaseDirectory(basePath);
            createPackageDirectory(basePath, pagesPackage);
            createPackageDirectory(basePath, stepsPackage);
            createHomePageClass(basePath, pagesPackage);
            createExampleTestClass(basePath, stepsPackage);
            log.info("Project structure generated successfully.");
        } catch (IOException e) {
            log.error("An error occurred while generating the project structure: " + e.getMessage());
        }
    }

    /**
     * Creates the base directory under which `pages` and `steps` will be created.
     *
     * @param basePath The base directory path where all packages will reside.
     * @throws IOException if an I/O error occurs when creating directories.
     */
    private static void createBaseDirectory(String basePath) throws IOException {
        File baseDirectory = new File(basePath);
        if (!baseDirectory.exists()) {
            baseDirectory.mkdirs();
        }
    }

    /**
     * Creates a specific package directory under a base path.
     *
     * @param basePath     The base directory path where all packages will reside.
     * @param packageName  The package name, e.g., "pages" or "steps".
     * @throws IOException if an I/O error occurs when creating directories.
     */
    private static void createPackageDirectory(String basePath, String packageName) throws IOException {
        String path = basePath + "/" + packageName.replace(".", "/");
        File packageDirectory = new File(path);
        if (!packageDirectory.exists()) {
            packageDirectory.mkdirs();
        }
    }

    /**
     * Generates the HomePage class in the specified package with explanatory comments.
     *
     * @param basePath     The base directory path where the package resides.
     * @param pagesPackage The `pages` package name.
     * @throws IOException if an I/O error occurs when writing to the file.
     */
    private static void createHomePageClass(String basePath, String pagesPackage) throws IOException {
        String filePath = basePath + "/" + pagesPackage.replace(".", "/") + "/HomePage.java";
        File file = new File(filePath);
        if (file.createNewFile()) {
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write("package " + pagesPackage + ";\n\n");
                writer.write("import base.BasePage;\n");
                writer.write("import core.annotations.FindByList;\n");
                writer.write("import core.annotations.FindByList.Locator;\n");
                writer.write("import org.openqa.selenium.WebElement;\n");
                writer.write("import org.openqa.selenium.support.FindBy;\n\n");
                writer.write("import static core.annotations.FindByList.*;\n");
                writer.write("import static utils.ElementUtils.waitAndClick;\n");
                writer.write("import static utils.ElementUtils.waitAndSendKeys;\n\n");
                writer.write("/**\n");
                writer.write(" * The `HomePage` class represents the main page in your automation framework.\n");
                writer.write(" * This class inherits from the `BasePage` to reuse core page functionalities.\n");
                writer.write(" *\n");
                writer.write(" * The web elements are initialized dynamically using the page factory pattern, allowing for a modular\n");
                writer.write(" * Page Object Model design. All page classes should extend `HomePage` or `BasePage` for consistency.\n");
                writer.write(" * @see BasePage\n");
                writer.write(" */\n");
                writer.write("public class HomePage extends BasePage {\n\n");
                writer.write("    /**\n");
                writer.write("     * The `FindByList` annotation allows specifying multiple locators for a single web element.\n");
                writer.write("     * Priority is based on the locator's efficiency: ID > Name > ClassName > CSS > XPath.\n");
                writer.write("     * This annotation is useful for dynamic elements.\n");
                writer.write("     * @see FindBy\n");
                writer.write("     * @see FindByList\n");
                writer.write("     */\n");
                writer.write("    @FindByList({\n");
                writer.write("        @Locator(type = LocatorType.ID, value = \"user-name\"),\n");
                writer.write("        @Locator(type = LocatorType.CSS, value = \"#user-name\"),\n");
                writer.write("        @Locator(type = LocatorType.XPATH, value = \"//input[@placeholder='Username']\")\n");
                writer.write("    })\n");
                writer.write("    public WebElement usernameInput;\n\n");
                writer.write("    @FindBy(id = \"password\")\n");
                writer.write("    public WebElement passwordInput;\n\n");
                writer.write("    @FindBy(id = \"login-button\")\n");
                writer.write("    public WebElement loginButton;\n\n");
                writer.write("    /**\n");
                writer.write("     * Opens the main page by navigating to its URL using the WebDriver instance.\n");
                writer.write("     */\n");
                writer.write("    public void openPage() {\n");
                writer.write("        driver.get(\"https://www.saucedemo.com/\");\n");
                writer.write("    }\n\n");
                writer.write("    /**\n");
                writer.write("     * Logs into the application by entering credentials and clicking the login button.\n");
                writer.write("     * Utility methods from `ElementUtils` are used for element interaction.\n");
                writer.write("     * @see utils.ElementUtils\n");
                writer.write("     */\n");
                writer.write("    public void doLogin() {\n");
                writer.write("        waitAndSendKeys(usernameInput, \"standard_user\");\n");
                writer.write("        waitAndSendKeys(passwordInput, \"secret_sauce\");\n");
                writer.write("        waitAndClick(loginButton);\n");
                writer.write("    }\n");
                writer.write("}\n");
            }
        }
    }

    /**
     * Generates the ExampleTest class in the specified package with explanatory comments.
     *
     * @param basePath     The base directory path where the package resides.
     * @param stepsPackage The `steps` package name.
     * @throws IOException if an I/O error occurs when writing to the file.
     */
    private static void createExampleTestClass(String basePath, String stepsPackage) throws IOException {
        String filePath = basePath + "/" + stepsPackage.replace(".", "/") + "/ExampleTest.java";
        File file = new File(filePath);
        if (file.createNewFile()) {
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write("package " + stepsPackage + ";\n\n");
                writer.write("import base.BaseTest;\n");
                writer.write("import org.junit.jupiter.api.Assertions;\n");
                writer.write("import org.junit.jupiter.api.Test;\n");
                writer.write("import pages.HomePage;\n\n");
                writer.write("/**\n");
                writer.write(" * The `ExampleTest` class demonstrates how to implement a test case using the automation framework.\n");
                writer.write(" * By extending `BaseTest`, the WebDriver lifecycle is managed automatically.\n");
                writer.write(" *\n");
                writer.write(" * Test classes should focus on abstractions and assertions, while page classes should handle\n");
                writer.write(" * page-specific actions and locators. This modularity ensures a maintainable and organized\n");
                writer.write(" * Page Object Model structure.\n");
                writer.write(" * @see BaseTest\n");
                writer.write(" */\n");
                writer.write("public class ExampleTest extends BaseTest {\n\n");
                writer.write("    /**\n");
                writer.write("     * The `exampleTest` method performs a basic test case for logging into the main page.\n");
                writer.write("     * It initializes a `HomePage` instance and calls its methods to navigate and log in.\n");
                writer.write("     * Assertions verify that the resulting page URL matches the expected one.\n");
                writer.write("     */\n");
                writer.write("    @Test\n");
                writer.write("    public void exampleTest() {\n");
                writer.write("        HomePage homePage = new HomePage();\n");
                writer.write("        homePage.openPage();\n");
                writer.write("        homePage.doLogin();\n");
                writer.write("        Assertions.assertEquals(\"https://www.saucedemo.com/inventory.html\", driver.getCurrentUrl());\n");
                writer.write("    }\n");
                writer.write("}\n");
            }
        }
    }

    /**
     * Deletes both the `pages` and `steps` directories recursively from `test/java/`.
     *
     * @param pagesPackage The package name of the `pages` directory.
     * @param stepsPackage The package name of the `steps` directory.
     */
    public static void deleteTestDirectories(String pagesPackage, String stepsPackage) {
        try {
            Path pagesPath = Paths.get("test/java", pagesPackage.replace(".", "/"));
            Path stepsPath = Paths.get("test/java", stepsPackage.replace(".", "/"));

            if (Files.exists(pagesPath)) {
                Files.walk(pagesPath)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
                log.info("Deleted the pages directory successfully: " + pagesPackage);
            }

            if (Files.exists(stepsPath)) {
                Files.walk(stepsPath)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
                log.info("Deleted the steps directory successfully: " + stepsPackage);
            }
        } catch (IOException e) {
            log.error("Failed to delete directories: " + pagesPackage + ", " + stepsPackage + " - " + e.getMessage());
        }
    }
}
