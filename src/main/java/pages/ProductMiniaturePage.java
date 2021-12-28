package pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@Getter
public class ProductMiniaturePage {
    private Logger logger = LoggerFactory.getLogger(ProductMiniaturePage.class);

    public ProductMiniaturePage(WebElement productMiniature) {
        PageFactory.initElements(new DefaultElementLocatorFactory(productMiniature), this);
    }

    @FindBy(css = ".product-thumbnail")
    private WebElement productImage;

    @FindBy(css = ".product-title")
    private WebElement productName;

    @FindBy(css = ".price")
    private WebElement priceAfterDiscount;

    @FindBy(css = ".regular-price")
    private WebElement regularPrice;

    @FindBy(css = ".discount-percentage")
    private WebElement discountPercentageInfo;

    @FindBy(css = ".quick-view")
    private WebElement quickViewElement;

    @FindBy(css = ".color")
    private List<WebElement> availableColors;

    @FindBy(css = ".product-flag")
    private WebElement priceDiscountOnImage;


    public String getProductName() {
        return productName.getText();
    }

    public BigDecimal getPriceAfterDiscount() {
        return new BigDecimal(priceAfterDiscount.getAttribute("innerHTML").replaceAll("zł", ""));
    }

    public BigDecimal getRegularPrice() {
        return new BigDecimal(regularPrice.getAttribute("innerHTML").replaceAll("zł", ""));
    }

    public int getDiscountPercentageInfo() {
        return Math.abs(Integer.parseInt(discountPercentageInfo
                .getAttribute("innerHTML")
                .replaceAll("%","")));
    }

    public boolean isPriceDisplayed(){
        return priceAfterDiscount.isDisplayed();
    }

    public boolean isRegularPriceDisplayed(){
        return regularPrice.isDisplayed();
    }

    public boolean isPriceCalculatedCorrectly(){
        MathContext mc = new MathContext(4);
        BigDecimal multiply = new BigDecimal(100- getDiscountPercentageInfo()).divide(new BigDecimal(100));
        BigDecimal priceAfterDiscount = getRegularPrice().multiply(multiply, mc);
        return priceAfterDiscount.equals(getPriceAfterDiscount());
    }
}
