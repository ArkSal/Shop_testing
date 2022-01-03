package pages.products;

import models.basket.Basket;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;
import providers.RandomDataGenerator;
import providers.TextFormatProvider;

import java.math.BigDecimal;
import java.math.MathContext;

public class ProductDetailsPage extends BasePage {
    private Logger logger = LoggerFactory.getLogger(ProductDetailsPage.class);
    private int quantity =1;

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".discount-percentage")
    private WebElement discountPercentageInfo;

    @FindBy(css = ".regular-price")
    private WebElement regularPrice;

    @FindBy(css = "[itemprop='price']")
    private WebElement price;

    @FindBy(css = "#quantity_wanted")
    private WebElement productQuantityField;

    @FindBy(css = "h1.h1")
    private WebElement productName;

    @FindBy(css = ".add-to-cart")
    private WebElement addToCartButton;

    @FindBy(css = "[name='submitCustomizedData']")
    private WebElement saveCustomizationButton;

    @FindBy(css = ".product-message")
    private WebElement customizationMessage;

    public boolean isDiscountDisplayed(){
        return isElementDisplayed(discountPercentageInfo);
    }

    public String getProductName(){
        return productName.getText();
    }

    public boolean isRegularPriceDisplayed(){
        return isElementDisplayed(regularPrice);
    }

    public boolean isPriceAfterDiscountDisplayed(){
        return isElementDisplayed(price);
    }

    public BigDecimal getPrice() {
        return TextFormatProvider.getBigDecimalFromStringWithCurrency(price.getAttribute("innerHTML"));
    }

    public BigDecimal getRegularPrice() {
        return TextFormatProvider.getBigDecimalFromStringWithCurrency(regularPrice.getAttribute("innerHTML"));
    }

    public int getDiscountPercentageInfo() {
     return TextFormatProvider.getIntFromString(discountPercentageInfo.getAttribute("innerHTML"));
    }

    public boolean isPriceCalculatedCorrectly(){
        MathContext mc = new MathContext(4);
        BigDecimal multiply = new BigDecimal(100- getDiscountPercentageInfo()).divide(new BigDecimal(100));
        BigDecimal priceAfterDiscount = getRegularPrice().multiply(multiply, mc);
        logger.info("Checking if price is calculated correctly");
        return priceAfterDiscount.equals(getPrice());
    }

    public ProductDetailsPage setProductQuantity(int productQuantity){
        sendKeysToElement(productQuantityField, String.valueOf(productQuantity));
        this.quantity = productQuantity;
        return this;
    }

    public ProductDetailsPage setRandomProductQuantityInRange(int range){
        int randomizedRange = new RandomDataGenerator().getRandomNumberInRangeMinMax(1, range);
        setProductQuantity(randomizedRange);
        logger.info("Randomized value {} set to {} product ", randomizedRange, getProductName());
        return this;
    }

    private boolean checkIfCustomizationNeeded(){
        try {
            return saveCustomizationButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void typeCustomizationMessageIfNeeded(){
        if (checkIfCustomizationNeeded()){
            logger.info("Customization message is needed");
            String keysToSend = environmentConfig.getProductCustomizationText();
            sendKeysToElement(customizationMessage, keysToSend);
            logger.info("Message {} typed in message window", keysToSend);
            clickOnElement(saveCustomizationButton);
            setProductQuantity(this.quantity);
            System.out.println(productQuantityField.getText());
        }
    }

    public ProductAddedToBasketPopupPage clickAddToCartButton(){
        typeCustomizationMessageIfNeeded();
        clickOnElement(addToCartButton);
        logger.info("Product {} added to basket", getProductName());
        ProductAddedToBasketPopupPage shoppingCartPage = new ProductAddedToBasketPopupPage(driver);
        logger.info("Waiting for window to be presented");
        waitForElementToBeVisible(shoppingCartPage.getProductAddedMessage());
        return shoppingCartPage;
    }

    public ProductDetailsPage addProductToDatabase(Basket basket){
        basket.addProductLineToBasket(getProductName(), getPrice(), quantity);
        return this;
    }
}
