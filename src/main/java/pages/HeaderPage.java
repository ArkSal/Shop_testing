package pages;

import lombok.Getter;
import models.WebListener;
import models.basket.Basket;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.internal.EventFiringMouse;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import providers.RandomDataGenerator;

import java.time.Duration;
import java.util.List;

@Getter
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

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    @FindBy(css = "input[type='text']")
    private WebElement searchTextField;

    @FindBy(css = ".ui-menu-item a")
    private List<WebElement> searchFieldAvailableProducts;

    @FindBy(css = "#top-menu >li")
    private List<WebElement> categoriesList;

    @FindBy(css = ".header .cart-products-count")
    private WebElement cartProductsCount;

    public HeaderPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage clickSignInButton() {
        signInButton.click();
        return new LoginPage(driver);
    }

    public int getProductsCount(){
        return Integer.parseInt(cartProductsCount.getText().replaceAll("[^0-9]",""));
    }

    public String getLoggerAccountFullName() {
        return loggedAccountInfo.getText();
    }

    public HeaderPage sendKeysToSearchTextField(String keysToSend){
        sendKeysToElement(searchTextField, keysToSend);
        return new HeaderPage(driver);
    }

    public ProductsGridsPage clickSearchButton(){
        clickOnElement(searchButton);
        return new ProductsGridsPage(driver);
    }

    public boolean checkIfProductExistInSearchField(String productName){
        try {
            new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(d->searchFieldAvailableProducts.size()>0);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Product not found in search catalog");
        }

        return searchFieldAvailableProducts.stream()
                .filter(o -> o.findElement(By.cssSelector(".product"))
                        .getText().equals(productName)).findFirst().isPresent();
    }

    public ProductsGridsPage clickRandomCategory(){
        clickOnElement(getRandomCategory());
        logger.info("Clicked on randomized category");
        return new ProductsGridsPage(driver);
    }

    private WebElement getRandomCategory(){
        WebElement element = categoriesList.get(new RandomDataGenerator()
                .getRandomNumberInRange(categoriesList.size()-1));
        logger.info("Randomized {} category", element.getText());
        return element;
    }

    public boolean isQuantityInCartIconCorrect(Basket basket){
        return basket.getTotalQuantity()!=getProductsCount();
    }




}
