package core;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class ConfigManager {
    public static void initProject() {
        String packageName = "pages";
        String[] classNames = {"BasePage", "LoginPage", "HomePage"};
        String[] classContents = {
                "public class BasePage {\n\n}",
                "public class LoginPage extends BasePage {\n\n}",
                "public class HomePage extends BasePage {\n\n}"
        };

        try {
            createDirectories(packageName);
            createClasses(packageName, classNames, classContents);
            createConfigFile();
            generateLoginPageTest(packageName, "LoginPage", "src/test/java/" + packageName.replace(".", "/"));
            log.info("Page Object Model structure generated successfully.");
        } catch (IOException e) {
            log.error("An error occurred while generating Page Object Model structure: " + e.getMessage());
        }
    }

    private static void createDirectories(String packageName) throws IOException {
        String[] packageParts = packageName.split("\\.");
        String path = "src/test/java";

        File rootDirectory = new File(path);
        if (!rootDirectory.exists()) {
            rootDirectory.mkdirs();
        }

        for (String part : packageParts) {
            path += "/" + part;
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdir();
            }
        }
    }

    private static void createClasses(String packageName, String[] classNames, String[] classContents) throws IOException {
        String path = "src/test/java/" + packageName.replace(".", "/");
        for (int i = 0; i < classNames.length; i++) {
            String filePath = path + "/" + classNames[i] + ".java";
            File file = new File(filePath);
            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(filePath);
                writer.write("package " + packageName + ";\n\n");
                writer.write(classContents[i]);
                writer.close();
            }
        }
    }

    private static void createConfigFile() throws IOException {
        String path = "config.properties";
        File configFile = new File(path);
        if (configFile.createNewFile()) {
            FileWriter writer = new FileWriter(path);
            writer.write("browser=chrome\n");
            writer.close();
        }
    }

    private static void generateLoginPageTest(String packageName, String className, String path) throws IOException {
        String testFilePath = path + "/" + className + "Test.java";
        File testFile = new File(testFilePath);
        if (testFile.createNewFile()) {
            FileWriter writer = new FileWriter(testFilePath);
            writer.write("package " + packageName + ";\n\n");
            writer.write("import base.BaseTest;\n");
            writer.write("import org.testng.annotations.Test;\n\n");
            writer.write("public class " + className + "Test extends BaseTest {\n\n");
            writer.write("\t@Test\n");
            writer.write("\tpublic void " + className + "Test(){\n");
            writer.write("\t\t// Implement your test logic here\n");
            writer.write("\t\t// Example: driver.get(\"your_login_page_url\");\n");
            writer.write("\t\t// Assert.assertTrue(yourAssertionCondition);\n");
            writer.write("\t}\n");
            writer.write("}\n");
            writer.close();
        }
    }
}
