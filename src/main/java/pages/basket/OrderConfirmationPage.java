package pages.basket;

import models.basket.Basket;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;
import pages.accounts.MyAccountPage;
import pages.common.HeaderPage;

import java.util.ArrayList;
import java.util.List;

public class OrderConfirmationPage extends BasePage {
    Logger logger = LoggerFactory.getLogger(OrderConfirmationPage.class);
    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".order-line.row")
    private List<WebElement> orderLineConfirmationList;

    @FindBy(css = "#order-details li:nth-of-type(1)")
    private WebElement orderReferenceLineText;

    @FindBy(css = "#order-details li:nth-of-type(2)")
    private WebElement paymentMethodLineText;

    @FindBy(css = "#order-details li:nth-of-type(3)")
    private WebElement shippingMethodLineText;

    public List<OrderLineConfirmationPage> createOrderLineList() {
        List<OrderLineConfirmationPage> orderLineList = new ArrayList<>();
        for (WebElement product : orderLineConfirmationList) {
            orderLineList.add(new OrderLineConfirmationPage(product));
        }
        logger.info("List of product in shopping cart created");
        return orderLineList;
    }

    public Basket readBasketFromConfirmationPage(){
        Basket orderConfirmationBasket = new Basket();
        for (OrderLineConfirmationPage orderLineConfirmationPage : createOrderLineList()) {
            orderConfirmationBasket.addProductLineToBasket(orderLineConfirmationPage.getProductFromOrder());
        }
        return orderConfirmationBasket;
    }

    public String getPaymentMethod(){
        switch(paymentMethodLineText.getText()){
            case "Payment method: Bank transfer":
                return "Bank transfer";

            case "Payment method: Payments by check":
                return "Payments by check";

            default:
                return null;
        }
    }

    public String getShippingMethod(){
        return shippingMethodLineText.getText();
    }

    public String getOrderReferenceNumber(){
        return StringUtils.substringAfter(orderReferenceLineText.getText(), ": ");
    }

    public MyAccountPage goToMyAccountPage(){
        return new HeaderPage(driver).clickMyAccount();
    }

}
