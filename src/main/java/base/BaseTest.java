package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import core.DriverManager;


public class BaseTest {
    protected static ExtentReports extent;
    protected ExtentTest test;
    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        extent = new ExtentReports();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("target/extent-reports/report.html");
        extent.attachReporter(htmlReporter);
    }

    @AfterClass
    public void tearDown() {
        extent.flush();
    }

    @Before
    public void beforeMethod() {
        test = extent.createTest(getClass().getSimpleName());
        driver = DriverManager.getDriver();
    }

    @After
    public void afterMethod() {
        if (driver != null) {
            driver.quit();
        }
    }

    public String takeScreenshot() {
        TakesScreenshot ts = (TakesScreenshot) driver;
        String screenshotBase64 = ts.getScreenshotAs(OutputType.BASE64);
        return "data:image/png;base64," + screenshotBase64;
    }
}
