package pages.products;

import lombok.Getter;
import models.basket.Basket;
import models.basket.BasketLine;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;
import pages.basket.ShoppingCartPage;
import providers.TextFormatProvider;

import java.math.BigDecimal;
@Getter
public class ProductAddedToBasketPopupPage extends BasePage {
    private Logger logger = LoggerFactory.getLogger(ProductAddedToBasketPopupPage.class);
    public ProductAddedToBasketPopupPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#myModalLabel")
    private WebElement productAddedMessage;

    @FindBy(css = ".modal-dialog .product-price")
    private WebElement price;

    @FindBy(css = ".product-name")
    private WebElement productName;

    @FindBy(css = ".product-quantity strong")
    private WebElement quantityOfProduct;

    @FindBy(css = ".product-total .value")
    private WebElement totalValue;

    @FindBy(css = ".btn-secondary")
    private WebElement continueShoppingButton;

    @FindBy(css = "a.btn-primary")
    private WebElement proceedToCheckoutButton;

    @FindBy(css = ".cart-content .cart-products-count")
    private WebElement itemsInCartQuantityMessage;

    public boolean isPopupHasCorrectValues(Basket basket){
        BasketLine foundBasket = basket.getBasketLine(getProductName());
        if(!foundBasket.getProductName().equals(getProductName())){
            logger.error("Product name should be: {}, in popup: {}", foundBasket.getProductName(), getProductName());
            return false;
        }
        if(!foundBasket.getProduct().getPrice().equals(getProductPrice())){
            logger.error("Product price should be: {}, in popup: {}", foundBasket.getProduct().getPrice(), getProductPrice());
            return false;
        }
        if(foundBasket.getQuantity()!=getProductQuantity()){
            logger.error("Product quantity should be: {}, in popup: {}", foundBasket.getQuantity(), getProductQuantity());
            return false;
        }
        if(!basket.getTotalSumInBasket().equals(getTotalValue())){
            logger.error("Product total value should be: {}, in popup: {}", basket.getTotalSumInBasket(), getTotalValue());
            return false;
        };
        String messageShouldBe = createQuantityProductsMessage(basket.getTotalQuantity());
        if(!getItemsInCartMessage().equals(messageShouldBe)){
            logger.error("Message should be: \"{}\", message in popup was: \"{}\"", getItemsInCartMessage(), messageShouldBe);
            return false;
        }
        return true;
    }

    public ProductAddedToBasketPopupPage clickContinueShopping(){
        clickOnElement(continueShoppingButton);
        return this;
    }

    public ShoppingCartPage clickProceedToCheckout(){
        clickOnElement(proceedToCheckoutButton);
        return new ShoppingCartPage(driver);
    }

    public String getProductName(){
        return productName.getText();
    }

    public BigDecimal getProductPrice(){
        return TextFormatProvider.getBigDecimalFromStringWithCurrency(price.getAttribute("innerHTML"));
    }

    public int getProductQuantity(){
        return Integer.parseInt(quantityOfProduct.getText());
    }

    public String getItemsInCartMessage(){
        return itemsInCartQuantityMessage.getText();
    }

    public BigDecimal getTotalValue(){
        return TextFormatProvider.getBigDecimalFromStringWithCurrency(totalValue.getAttribute("innerHTML"));
    }

    public String createQuantityProductsMessage(int quantity){
        if(quantity==1){
            return "There is " + quantity + " item in your cart.";
        }
        return "There are " + quantity + " items in your cart.";
    }
}
