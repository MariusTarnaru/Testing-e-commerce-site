package utils;

import io.cucumber.java.Scenario;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTestClass extends PageObject {

    Scenario scenario;

    @Managed
    public WebDriver driver;


    @Before
    public WebDriver setUpTest() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/data.properties");
        prop.load(fis);
        String browserName = prop.getProperty("browser");
        String driverName = prop.getProperty("webdriver");
        String url = prop.getProperty("url");

       if (browserName.equalsIgnoreCase("chrome")){
            if (SystemUtils.IS_OS_WINDOWS) {
                driver = getDriver();
                driver.manage().window().maximize();
                driver.manage().deleteAllCookies();
            }
        }
        else if(browserName.equalsIgnoreCase("firefox")){
            //execute in geckodriver  https://github.com/mozilla/geckodriver/releases
            driver = new FirefoxDriver();
        }
        else if (browserName == "Edge"){
            //execute in edge driver  https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/
            driver = new EdgeDriver();
        }
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
        driver.get(url);
        return driver;

    }


    /*@Before
    public void setUpTest() {

        System.setProperty("webdriver.gecko.driver",  "/src/test/resources/drivers/geckodriver");

        driver = new FirefoxDriver();

        if (SystemUtils.IS_OS_WINDOWS) {
            driver.manage().window().maximize();
        }
    }*/

    @After
    public void tearDown() {
        driver = getDriver();
        final TakesScreenshot screenshot = (TakesScreenshot) driver;
        final byte[] data = screenshot.getScreenshotAs(OutputType.BYTES);
        scenario.attach(data, "image/png", "Screenshot after scenario");
        driver.close();
        driver.quit();
    }
}
