package pages.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;
import pages.products.OnSalePage;

public class FooterPage extends BasePage {
    private Logger logger = LoggerFactory.getLogger(FooterPage.class);

    @FindBy(css = "[placeholder='Your email address']")
    WebElement emailField;

    @FindBy(css = "[value='Subscribe']")
    WebElement subscribeButton;

    @FindBy(css = "#link-product-page-prices-drop-1")
    private WebElement pricesDrop;

    public FooterPage(WebDriver driver) {
        super(driver);
    }

    public OnSalePage goToOnSalePage(){
        clickOnElement(pricesDrop);
        logger.info("Prices drop element clicked");
        return new OnSalePage(driver);
    }
}
