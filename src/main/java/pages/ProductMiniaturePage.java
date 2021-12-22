package pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class ProductMiniaturePage {
    private Logger logger = LoggerFactory.getLogger(ProductMiniaturePage.class);

    public ProductMiniaturePage(WebElement productMiniature) {
        PageFactory.initElements(new DefaultElementLocatorFactory(productMiniature), this);
    }

    @FindBy(css = ".product-thumbnail")
    private WebElement productImage;

    @FindBy(css = ".product-title a")
    private WebElement productName;

    @FindBy(css = ".price")
    private WebElement price;

    @FindBy(css = ".regular-price")
    private WebElement priceBeforeDiscount;

    @FindBy(css = ".discount-percentage")
    private WebElement discountPercentageValue;

    @FindBy(css = ".quick-view")
    private WebElement quickViewElement;

    @FindBy(css = ".color")
    private List<WebElement> availableColors;


    public String getProductName() {
        return productName.getText();
    }

    public BigDecimal getPrice() {
        return new BigDecimal(price.getText());
    }

    public BigDecimal getPriceBeforeDiscount() {
        return new BigDecimal(priceBeforeDiscount.getText());
    }

    public int getDiscountPercentageValue() {
        return Integer.parseInt(discountPercentageValue.getText());
    }
}
