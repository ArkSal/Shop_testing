package pages.basket;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;

public class PaymentPage extends BasePage {
    public PaymentPage(WebDriver driver) {
        super(driver);
    }
    private Logger logger = LoggerFactory.getLogger(PaymentPage.class);

    @FindBy(css = "[for='payment-option-2']")
    private WebElement payByBankOption;

    @FindBy(css = "[for='conditions_to_approve[terms-and-conditions]']")
    private WebElement termsOfServiceCondition;

    @FindBy(css = "#payment-confirmation button")
    private WebElement placeOrderButton;

    @FindBy(css = "#cta-terms-and-conditions-0")
    private WebElement termsOfServiceOpenButton;

    @FindBy(css = "#modal .close")
    private WebElement closePopupIcon;

    @FindBy(css = ".js-modal-content")
    private WebElement termsAndConditionsRolesPopup;

    public PaymentPage pickPayByBankWireOption(){
        clickOnElement(payByBankOption);
        return this;
    }

    public OrderConfirmationPage clickPlaceOrderButton(){
        clickOnElement(placeOrderButton);
        return new OrderConfirmationPage(driver);
    }

    public PaymentPage acceptTerms(){
        clickOnElement(termsOfServiceCondition);
        return this;
    }

    public PaymentPage openTermsOfServicePopup(){
        clickOnElement(termsOfServiceOpenButton);
        return this;
    }


    public boolean checkIfTermsPopupHasText(){
        waitForElementToBeVisible(termsAndConditionsRolesPopup);
        boolean terms = termsAndConditionsRolesPopup.getText().isEmpty();
        if(terms) {
            logger.error("No text in terms and conditions popup");
        }
        else{
            logger.info("There are text rules in terms and conditions popup");
        }
        return !terms;
    }

    public PaymentPage closeRulesPopup(){
        clickOnElement(closePopupIcon);
        return this;
    }

}
