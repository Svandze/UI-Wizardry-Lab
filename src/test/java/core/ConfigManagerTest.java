package core;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ConfigManagerTest {

    @Test
    void testInitProject() {
        ConfigManager.initProject();
        File basePageFile = new File("src/test/java/pages/BasePage.java");
        assertTrue(basePageFile.exists());

        File configFile = new File("config.properties");
        assertTrue(configFile.exists());

        ConfigManager.deletePagesDirectory("pages");
    }
}