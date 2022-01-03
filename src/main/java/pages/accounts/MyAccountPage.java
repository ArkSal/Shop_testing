package pages.accounts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class MyAccountPage extends BasePage {
    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#history-link")
    private WebElement orderHistoryAndDetailsIcon;

    public OrderHistoryPage clickOrderHistoryAndDetailsIcon(){
        clickOnElement(orderHistoryAndDetailsIcon);
        return new OrderHistoryPage(driver);
    }
}
