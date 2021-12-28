package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import providers.RandomDataGenerator;

import java.math.BigDecimal;
import java.math.MathContext;

public class ProductDetailsPage extends BasePage{
    private Logger logger = LoggerFactory.getLogger(ProductDetailsPage.class);

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".discount-percentage")
    private WebElement discountPercentageInfo;

    @FindBy(css = ".regular-price")
    private WebElement regularPrice;

    @FindBy(css = "[itemprop='price']")
    private WebElement priceAfterDiscount;

    @FindBy(css = "#quantity_wanted")
    private WebElement productQuantityField;

    @FindBy(css = "[itemprop='itemListElement']:last-of-type")
    private WebElement productName;

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
        return isElementDisplayed(priceAfterDiscount);
    }

    public BigDecimal getPriceAfterDiscount() {
        return new BigDecimal(priceAfterDiscount.getAttribute("innerHTML").replaceAll("zł", ""));
    }

    public BigDecimal getRegularPrice() {
        return new BigDecimal(regularPrice.getAttribute("innerHTML").replaceAll("zł", ""));
    }

    public int getDiscountPercentageInfo() {
        return Integer.parseInt(discountPercentageInfo
                .getAttribute("innerHTML")
                .replaceAll("\\D+",""));
    }

    public boolean isPriceCalculatedCorrectly(){
        MathContext mc = new MathContext(4);
        BigDecimal multiply = new BigDecimal(100- getDiscountPercentageInfo()).divide(new BigDecimal(100));
        BigDecimal priceAfterDiscount = getRegularPrice().multiply(multiply, mc);
        logger.info("Checking if price is calculated correctly");
        return priceAfterDiscount.equals(getPriceAfterDiscount());
    }

    public ProductDetailsPage setProductQuantity(int productQuantity){
        sendKeysToElement(productQuantityField, String.valueOf(productQuantity));
        return this;
    }

    public ProductDetailsPage setRandomProductQuantityInRange(int range){
        int randomizedRange = new RandomDataGenerator().getRandomNumberInRangeMinMax(1, range);
        sendKeysToElement(productQuantityField, String.valueOf(randomizedRange));
        logger.info("Randomized value {} set to {} product ", randomizedRange, getProductName());
        return this;
    }
}
