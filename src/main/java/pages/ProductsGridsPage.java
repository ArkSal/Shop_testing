package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import providers.RandomDataGenerator;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductsGridsPage extends BasePage{
    private Logger logger = LoggerFactory.getLogger(ProductsGridsPage.class);

    @FindBy(css = ".product-miniature")
    private List<WebElement> productsMiniatures;

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

    public boolean checkIfProductExistInGrid(String productName){
        return createProductMiniaturesList().stream()
                .filter(o -> o.getProductName()
                        .equals(productName)).findFirst().isPresent();
    }

    public ProductsGridsPage(WebDriver driver) {
        super(driver);
    }

    public int getProductsQuantity(){
        return productsMiniatures.size();
    }

    public ProductMiniaturePage getRandomProductPage(){
        List<ProductMiniaturePage> temporaryList = createProductMiniaturesList();
        logger.info("Randomized product miniature");
        return temporaryList.get
                (new RandomDataGenerator()
                        .getRandomNumberInRange(temporaryList.size()-1));
    }

    public ProductDetailsPage clickOnRandomProductMiniature(){
        ProductMiniaturePage productMiniaturePage = getRandomProductPage();
        String productName = productMiniaturePage.getProductName();
        clickOnElement(productMiniaturePage.getProductImage());
        logger.info("Clicked on randomized {} product miniature", productName);
        return new ProductDetailsPage(driver);
    }
}
