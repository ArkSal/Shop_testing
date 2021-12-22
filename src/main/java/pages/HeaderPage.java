package pages;

import models.WebListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.internal.EventFiringMouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeaderPage extends BasePage {
    private Logger logger = LoggerFactory.getLogger(HeaderPage.class);
    private EventFiringMouse eventFiringMouse;
    private WebListener webListener;

    @FindBy(css = "#category-3 > a")
    private WebElement clothesCategoryButton;

    @FindBy(css = "#category-6 > a")
    private WebElement accessoriesCategoryButton;

    @FindBy(css = "#category-9 > a")
    private WebElement artCategoryButton;

    @FindBy(css = "a[title^='Log in']")
    private WebElement signInButton;

    @FindBy(css = ".account")
    private WebElement loggedAccountInfo;

    public HeaderPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage clickSignInButton() {
        signInButton.click();
        return new LoginPage(driver);
    }

    public String getLoggerAccountFullName() {
        return loggedAccountInfo.getText();
    }
}
