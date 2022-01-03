package pages.accounts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryPage extends BasePage {
    Logger logger = LoggerFactory.getLogger(OrderHistoryPage.class);
    public OrderHistoryPage(WebDriver driver) {
        super(driver);
    }
    @FindBy(css = "tbody tr")
    private List<WebElement> orderHistoryList;

    public List<OrderHistoryLinePage> createProductsList() {
        List<OrderHistoryLinePage> orderHistory = new ArrayList<>();
        for (WebElement order : orderHistoryList) {
            orderHistory.add(new OrderHistoryLinePage(order, driver));
        }
        logger.info("List of orders created");
        return orderHistory;
    }

    public OrderHistoryLinePage getOrderHistoryLineByReferenceNumber(String referenceNumber){
        return createProductsList().stream().filter(order->referenceNumber.equals(order.getOrderNumber())).findAny().orElse(null);
    }
}
