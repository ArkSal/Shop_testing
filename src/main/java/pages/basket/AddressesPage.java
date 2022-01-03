package pages.basket;

import models.address.Address;
import models.user.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;

import java.util.List;

public class AddressesPage extends BasePage {
    public AddressesPage(WebDriver driver) {
        super(driver);
    }
    private Logger logger = LoggerFactory.getLogger(AddressesPage.class);

    @FindBy(css = "[name='address1']")
    private WebElement addressField;

    @FindBy(css = "[name='city']")
    private WebElement cityField;

    @FindBy(css = "[name='postcode']")
    private WebElement postalCodeField;

    @FindBy(css = "[name='id_country']")
    private WebElement countryField;

    @FindBy(css = "[name='id_country']  option:not(:first-child)")
    private List<WebElement> countryOptions;

    @FindBy(css = "[name='confirm-addresses']")
    private WebElement continueButton;

    public AddressesPage fillAndSaveRequiredFieldsWithRandomizedPolishData(Address addressToSave, User user){
        addressToSave.setFirstName(user.getFirstName());
        addressToSave.setLastName(user.getLastName());
        sendKeysToElement(addressField, addressToSave.getAddress());
        sendKeysToElement(cityField, addressToSave.getCity());
        sendKeysToElement(postalCodeField, addressToSave.getPostalCode());
        pickPolishCountry(addressToSave);
        return this;
    }

    private AddressesPage pickPolishCountry(Address addressToSave){
        clickOnElement(countryField);
        String country = countryOptions.get(0).getText();
        logger.info("Picked {} in country field", country);
        clickOnElement(countryOptions.get(0));
        addressToSave.setCountry(country);
        return this;
    }

    public ShippingMethodPage clickContinueButton(){
        clickOnElement(continueButton);
        return new ShippingMethodPage(driver);
    }

}
