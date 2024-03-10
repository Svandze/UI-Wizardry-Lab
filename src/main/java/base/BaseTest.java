package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import core.DriverManager;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class BaseTest {
    protected static ExtentReports extent;
    protected ExtentTest test;
    protected WebDriver driver;

    @BeforeSuite
    public void setUp() {
        extent = new ExtentReports();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("target/extent-reports/report.html");
        extent.attachReporter(htmlReporter);
    }

    @AfterSuite
    public void tearDown() {
        extent.flush();
    }

    @BeforeMethod
    public void beforeMethod() {
        test = extent.createTest(getClass().getSimpleName());
        driver = DriverManager.getDriver();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (driver != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                String screenshotPath = takeScreenshot();
                test.fail(result.getThrowable());
                test.addScreenCaptureFromPath(screenshotPath);
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                test.pass("Test passed");
            } else if (result.getStatus() == ITestResult.SKIP) {
                test.skip("Test skipped");
            }
            driver.quit();
        }
    }

    public String takeScreenshot() {
        TakesScreenshot ts = (TakesScreenshot) driver;
        String screenshotBase64 = ts.getScreenshotAs(OutputType.BASE64);
        return "data:image/png;base64," + screenshotBase64;
    }
}
