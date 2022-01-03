package pages.basket;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class ShippingMethodPage extends BasePage {
    public ShippingMethodPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "[name='confirmDeliveryOption']")
    private WebElement continueButton;

    public PaymentPage clickOnContinueButton(){
        clickOnElement(continueButton);
        return new PaymentPage(driver);
    }
}
