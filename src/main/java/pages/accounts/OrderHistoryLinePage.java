package pages.accounts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;
import providers.TextFormatProvider;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderHistoryLinePage extends BasePage {
    Logger logger = LoggerFactory.getLogger(OrderHistoryLinePage.class);
    public OrderHistoryLinePage(WebElement orderHistory, WebDriver driver) {
        super(driver);
        PageFactory.initElements(new DefaultElementLocatorFactory(orderHistory), this);
    }

    @FindBy(css = "[scope='row']")
    private WebElement orderReference;

    @FindBy(css = "td:nth-of-type(2)")
    private WebElement totalPrice;

    @FindBy(css = "td:nth-of-type(3)")
    private WebElement payment;

    @FindBy(css = "td")
    private WebElement orderDate;

    @FindBy(css = "td .label")
    private WebElement status;

    @FindBy(css = "[data-link-action='view-order-details']")
    private WebElement detailsButton;

    public String getOrderNumber(){
        return orderReference.getText();
    }

    public BigDecimal getTotalPrice(){
        return TextFormatProvider.getBigDecimalFromStringWithCurrency(totalPrice.getText());
    }

    public String getPaymentText(){
        return payment.getText();
    }

    public String getStatusText(){
        return status.getText();
    }

    public SingleOrderDetailsPage clickOnDetailsIcon(){
        clickOnElement(detailsButton);
        return new SingleOrderDetailsPage(driver);
    }

    public LocalDate getDate(){
        return TextFormatProvider.getLocalDateFromString(orderDate.getText());
    }

    public boolean isDateEqualsToday(){
        LocalDate todayDate = LocalDate.now();
        LocalDate dateFromOrderHistory = getDate();

        if(!todayDate.equals(dateFromOrderHistory)){
            logger.error("Dates are not equal. Today is: {},  date in order history is: {}", dateFromOrderHistory, todayDate);
            return false;
        }
        return true;
    }

}
