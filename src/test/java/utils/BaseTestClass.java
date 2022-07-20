package utils;

import com.codeborne.selenide.Configuration;
import io.cucumber.java.Scenario;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.webdriver.WebDriverFacade;
import net.thucydides.core.webdriver.WebDriverFactory;
import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static net.serenitybdd.core.Serenity.getDriver;

public class BaseTestClass {

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
            //execute in chrome driver
            System.setProperty(driverName, "D://chromedriver.exe");
            Map<String, Object> prefs = new HashMap<String, Object>();
            // Pass the argument 1 to allow and 2 to block
            prefs.put("profile.default_content_setting_values.cookies", 2);
            prefs.put("network.cookie.cookieBehavior", 2);
            prefs.put("profile.block_third_party_cookies", true);
            prefs.put("intl.accept_languages", "en-US");
            Configuration.browser = browserName;
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("prefs", prefs);
            Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);

            //driver = new WebDriverFacade(WebDriver.class, new WebDriverFactory());

            if (SystemUtils.IS_OS_WINDOWS) {
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
    public void tearDown() throws IOException {
        //WebDriver driver = getDriver();
        /*File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File("src/test/resources/screenshot.png"));*/
        //WebDriverFacade driver = (WebDriverFacade) getWebdriverManager().getWebdriver();
        //WebDriverFacade driverFacade = new WebDriverFacade(UserPOM.class, new WebDriverFactory());

        //driver = getRealDriver();
        driver = (WebDriverFacade) getDriver();
        final TakesScreenshot screenshot = (TakesScreenshot) driver;
        final byte[] data = screenshot.getScreenshotAs(OutputType.BYTES);
        scenario.attach(data, "image/png", "Screenshot after scenario");
        driver.close();
        driver.quit();
    }
}
