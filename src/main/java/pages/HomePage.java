package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import providers.RandomDataGenerator;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage{
    private Logger logger = LoggerFactory.getLogger(HomePage.class);

    @FindBy(css = ".product-miniature")
    private List<WebElement> productsMiniatures;

    @FindBy(css = "[role='listbox'] li")
    private List<WebElement> listBoxItems;

    @FindBy(css = "[data-slide='prev']")
    private WebElement previousListBoxItem;

    @FindBy(css = "[data-slide='next']")
    private WebElement nextListBoxItem;

    public List<ProductMiniaturePage> createProductMiniaturesList() {
        List<ProductMiniaturePage> productMiniatureList = new ArrayList<>();
        for (WebElement product : productsMiniatures) {
            productMiniatureList.add(new ProductMiniaturePage(product));
        }
        logger.info("List of product miniatures created");
        return productMiniatureList;
    }

    public ProductMiniaturePage getRandomProductFromPopularProductsList(){
        List<ProductMiniaturePage> productMiniaturesList = createProductMiniaturesList();
        return createProductMiniaturesList().get(new RandomDataGenerator().getRandomNumberInRange(
                (productMiniaturesList.size()-1)));
    }

    public String getRandomProductName(){
        return getRandomProductFromPopularProductsList().getProductName();
    }

    public HomePage cycleThroughSlider() {
        for (WebElement element : listBoxItems) {
            waitUntilElementTagContainsText(element, "class", "active");
            actions.click(nextListBoxItem).build().perform();
        }
        return this;
    }











    public HomePage(WebDriver driver) {
        super(driver);
    }
}
