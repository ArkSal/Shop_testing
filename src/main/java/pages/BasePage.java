package pages;

import models.UserDatabase;
import models.configuration.EnvironmentConfig;
import org.openqa.selenium.By;
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
import java.util.List;
import java.util.Random;

public abstract class BasePage {
    private Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;
    protected Actions actions;
    protected EnvironmentConfig environmentConfig;
    protected WebDriverWait wait;
    protected UserDatabase userDatabase;

    public BasePage(WebDriver driver) {
        environmentConfig = ConfigProvider.getConfig();
        userDatabase = new UserDatabase();
        this.driver = driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(environmentConfig.getWait())));
        PageFactory.initElements(driver, this);
    }

    public void clickOnElement(WebElement elementToClick) {
        String elementText =
        wait.until(ExpectedConditions
                .visibilityOf(elementToClick)).getText();
        elementToClick.click();
        logger.info("Clicked on webelement {}", elementText);
    }

    public void sendKeysToElement(WebElement elementToSendKeys, String keyToSend) {
        elementToSendKeys.clear();
        wait.until(ExpectedConditions
                .visibilityOf(elementToSendKeys)).sendKeys(keyToSend);
        logger.info("String {} sent to {} element", keyToSend, elementToSendKeys.getAttribute("class"));
    }

    protected void waitUntilElementTagContainsText(WebElement webElement, String tagName, String tagValue){
        wait.until(ExpectedConditions.
                attributeContains(webElement, tagName, tagValue));
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

    protected WebElement getRandomElementFromList(List<WebElement> webElements){
        return webElements.get(new Random().nextInt(webElements.size()-1));
    }

    protected void waitForTextToBePresented(WebElement webElement, String textToBeAppeared){
        wait.until(ExpectedConditions
                .textToBePresentInElement(webElement, textToBeAppeared));
    }

    protected void waitForElementToBeClickable(WebElement webElement) {
        wait.until(ExpectedConditions
                .elementToBeClickable(webElement));
    }

    protected void waitForElementToBeVisible(WebElement webElement) {
        wait.until(ExpectedConditions
                .visibilityOf(webElement));
    }

    protected WebElement getElementWhenTextAppear(By locator, String textToAppear) {
        wait.until(ExpectedConditions
                .textToBePresentInElementLocated(locator, textToAppear));
        return driver.findElement(locator);
    }

    protected WebElement getElementWhenClickable(By locator) {
        return wait.until(ExpectedConditions
                .elementToBeClickable(locator));
    }

    protected WebElement getElementWhenVisible(By locator) {
        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(locator));
    }

    protected List<WebElement> getElementsListWhenVisible(By locator){
        return wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(locator));
    }

}
