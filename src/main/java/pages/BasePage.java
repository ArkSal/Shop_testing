package pages;

import models.basket.Basket;
import models.basket.BasketLine;
import models.basket.Product;
import models.configuration.EnvironmentConfig;
import models.user.UserDatabase;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import providers.ConfigProvider;

import java.time.Duration;

public abstract class BasePage {
    private Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;
    protected Actions actions;
    protected EnvironmentConfig environmentConfig;
    protected WebDriverWait wait;
    protected UserDatabase userDatabase;
    protected Basket basket;
    protected BasketLine basketLine;
    protected Product product;

    public BasePage(WebDriver driver) {
        environmentConfig = ConfigProvider.getConfig();
        userDatabase = new UserDatabase();
        this.driver = driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(environmentConfig.getWait()));
        PageFactory.initElements(driver, this);
    }

    public void clickOnElement(WebElement elementToClick) {
        String elementText =
        wait.until(ExpectedConditions
                .elementToBeClickable(elementToClick)).getText();
        elementToClick.click();
        logger.info("Clicked on webelement {}", elementText);
    }

    public void sendKeysToElement(WebElement elementToSendKeys, String keyToSend) {
        elementToSendKeys.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        wait.until(ExpectedConditions
                .visibilityOf(elementToSendKeys)).sendKeys(keyToSend);
        logger.info("String {} sent to {} element", keyToSend, elementToSendKeys.getAttribute("class"));
    }

    protected boolean isElementDisplayed(WebElement webElement){
        boolean isDisplayed = wait.until(ExpectedConditions.visibilityOf(webElement)).isDisplayed();
        if(isDisplayed){
            logger.info("{} element is displayed", webElement.getText());
        }
        else{
            logger.info("{} element is not displayed", webElement.getText());
        }
        return isDisplayed;
    }

    protected void waitForElementToBeVisible(WebElement webElement) {
        wait.until(ExpectedConditions
                .visibilityOf(webElement));
    }




}
