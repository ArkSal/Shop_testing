package pages.basket;

import models.basket.BasketLine;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import providers.TextFormatProvider;

import java.math.BigDecimal;

public class OrderLineConfirmationPage  {
    public OrderLineConfirmationPage(WebElement orderLineConfirmation) {
        PageFactory.initElements(new DefaultElementLocatorFactory(orderLineConfirmation), this);
    }

    @FindBy(css = ".details span")
    private WebElement productName;

    @FindBy(css = ".qty .row :nth-of-type(1)")
    private WebElement productPrice;

    @FindBy(css = ".qty :nth-of-type(2)")
    private WebElement productQuantity;

    @FindBy(css = ".qty :nth-of-type(3)")
    private WebElement productTotalPrice;

    public BigDecimal getPriceInBigDecimal(){
        return TextFormatProvider.getBigDecimalFromStringWithCurrency(productPrice.getText());
    }

    public int getProductQuantity(){
        return TextFormatProvider.getIntFromString(productQuantity.getText());
    }

    public String getProductName(){
        return StringUtils.substringBefore(productName.getText()," -");
    }

    public BigDecimal getTotalProductPrice(){
        return TextFormatProvider.getBigDecimalFromStringWithCurrency(productTotalPrice.getText());
    }

    public BasketLine getProductFromOrder(){
        return new BasketLine(getProductName(), getPriceInBigDecimal(), getProductQuantity(), getTotalProductPrice());
    }

}
