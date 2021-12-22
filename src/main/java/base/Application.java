package base;

import models.configuration.EnvironmentConfig;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;
import pages.HeaderPage;
import providers.ConfigProvider;

public class Application extends BasePage {
    private Logger logger = LoggerFactory.getLogger(Application.class);
    protected EnvironmentConfig environmentConfig = ConfigProvider.getConfig();

    public Application(WebDriver driver) {
        super(driver);
    }

    public HeaderPage open() {
        String applicationAddress = environmentConfig.getUlr();
        logger.info("Opening application at: {}", applicationAddress);

        driver.get(applicationAddress);
        driver.manage().window().maximize();
        return new HeaderPage(driver);
    }

    public void close() {
        logger.info("Closing application");
        driver.quit();
    }
}
