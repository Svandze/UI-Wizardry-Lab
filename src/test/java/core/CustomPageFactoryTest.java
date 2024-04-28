package core;

import base.BasePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CustomPageFactoryTest {

    @Mock
    private WebDriver driver;
    private BasePage page;

    @BeforeEach
    void setUp() {
        driver = mock(WebDriver.class);
        MockitoAnnotations.initMocks(this);
        page = new BasePage(driver);
        CustomPageFactory.initElements(driver, page);
    }

    @Test
    void testInitElements() {
        assertNotNull(page);
    }

}