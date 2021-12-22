package base.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import models.Browsers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    //    private  EventFiringWebDriver driver;
//    private  WebListener webListener;
//    private WebDriver webdriver;
    protected WebDriver driver;

    public WebDriver getDriverOptions(Browsers browser) {
        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = getChromeDriver();
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver = getFirefoxDriver();
                break;
        }
        return driver;
    }

    private WebDriver getChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");
        return new ChromeDriver(options);
    }

    private WebDriver getFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }

}
