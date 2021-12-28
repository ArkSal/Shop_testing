package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class OnSalePage extends BasePage{
    private Logger logger = LoggerFactory.getLogger(OnSalePage.class);
    private ProductsGridsPage productsGridsPage;

    public OnSalePage(WebDriver driver) {
        super(driver);
        productsGridsPage = new ProductsGridsPage(driver);
    }

    @FindBy(css = ".category-top-menu")
    private WebElement categoryTitle;

    @FindBy(css = ".total-products")
    private WebElement totalProductsMessage;

    @FindBy(css = "#js-product-list-header")
    private WebElement pageTitle;



    public int getProductsQuantityFromMessage(){
        return Integer.parseInt(totalProductsMessage.getText().replaceAll("\\D+",""));
    }
    
   public boolean isOnSaleTitleDisplayed(){
        logger.info("Checking is correct title on sale page displayed");
        return pageTitle.getText().equals(environmentConfig.getOnSalePageTitle());
    }

    public boolean isPriceDropDisplayedOnEveryProduct(String priceDropValue){
        logger.info("Checking if price drop is displayed on every product on sales page");
        List<ProductMiniaturePage> productMiniaturesList = productsGridsPage.createProductMiniaturesList();
        for (ProductMiniaturePage productMiniaturePage : productMiniaturesList) {
            if(productMiniaturePage.getPriceDiscountOnImage().getText().equals("-" + priceDropValue)){
                logger.error("Price drop not displayed on product");
                return false;
            }
        }
        return true;
    }

    public boolean isRegularAndDiscountPriceDisplayedOnEveryProduct(){
        logger.info("Checking if regular and discount price is displayed on every product on sales page");
        List<ProductMiniaturePage> productMiniaturesList = productsGridsPage.createProductMiniaturesList();
        for (ProductMiniaturePage productMiniaturePage : productMiniaturesList) {
            if(!productMiniaturePage.isPriceDisplayed()){
                logger.error("Price after discount is not displayed on product");
                return false;
            }
            if(!productMiniaturePage.isRegularPriceDisplayed()){
                logger.error("Regular price is not displayed on product");
                return false;
            }
        }
        return true;
    }

    public boolean isEveryDiscountPriceCalculatedProperly(){
        logger.info("Checking if price drop is calculated properly on every product on sales page");
        List<ProductMiniaturePage> productMiniaturesList = productsGridsPage.createProductMiniaturesList();
        for (ProductMiniaturePage productMiniaturePage : productMiniaturesList) {
            if(!productMiniaturePage.isPriceCalculatedCorrectly()){
                logger.error("Price drop not calculated properly");
                return false;
            }
        }
        logger.info("Price drops calculated properly");
        return true;
    }


}
