package pages.basket;

import lombok.Getter;
import models.basket.BasketLine;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import pages.BasePage;
import providers.TextFormatProvider;

import java.math.BigDecimal;

@Getter
public class ShoppingCartProductPage extends BasePage {
    public ShoppingCartProductPage(WebElement shoppingCartProduct, WebDriver driver) {
        super(driver);
        PageFactory.initElements(new DefaultElementLocatorFactory(shoppingCartProduct), this);
    }

    @FindBy(css = ".product-line-info a")
    private WebElement productName;

    @FindBy(css = ".product-line-info .price")
    private WebElement singleProductPrice;

    @FindBy(css = ".js-cart-line-product-quantity")
    private WebElement singleProductQuantityField;

    @FindBy(css = ".js-increase-product-quantity")
    private WebElement quantityIncreaseButton;

    @FindBy(css = ".js-decrease-product-quantity")
    private WebElement quantityDecreaseButton;

    @FindBy(css = ".product-price strong")
    private WebElement singleProductTotalPrice;

    @FindBy(css = ".remove-from-cart")
    private WebElement removeItemFromBasketIcon;

    public String getProductName() {
        return productName.getText();
    }

    public BigDecimal getPrice() {
        return TextFormatProvider.getBigDecimalFromStringWithCurrency(singleProductPrice.getText());
    }

    public int getQuantity(){
        return TextFormatProvider.getIntFromString(singleProductQuantityField.getAttribute("value"));
    }

    public WebElement getRemoveItemFromBasketIcon() {
        return removeItemFromBasketIcon;
    }

    public BasketLine getProductFromCart(){
        return new BasketLine(getProductName(), getPrice(), getQuantity());
    }

    public WebElement getSingleProductQuantityField() {
        return singleProductQuantityField;
    }
}
