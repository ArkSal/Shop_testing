package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class CategoryPage extends BasePage {
    Logger logger = LoggerFactory.getLogger(CategoryPage.class);

    @FindBy(css = ".block-category h1")
    private WebElement categoryBoxHeader;

    @FindBy(css = ".category-top-menu")
    private WebElement categoryTitle;

    @FindBy(css = "#search_filters")
    private WebElement searchFiltersWindow;

    @FindBy(css = ".total-products")
    private WebElement totalProductsMessage;

    private ProductsGridsPage productsGridsPage;

    public CategoryPage(WebDriver driver) {
        super(driver);
        productsGridsPage = new ProductsGridsPage(driver);
    }

    public boolean isFilterMenuDisplayed() {
        try {
            searchFiltersWindow.isDisplayed();
            logger.info("Filter menu is displayed");
            return true;
        } catch (Exception e) {
            logger.error("Filter menu is not displayed");
            return false;
        }
    }

    public int getProductsQuantityFromMessage(){
        return Integer.parseInt(totalProductsMessage.getText().replaceAll("\\D+",""));
    }

    public boolean checkIfQuantityOfProductsInMessageIsCorrect(){
        return getProductsQuantityFromMessage()==getProductsGridsPage().getProductsQuantity();
    }

    public CategoryPage addMessageToListIfQuantityOfProductNotEqual(List<String> listToAddBug){
        int productQuantityInMessage = getProductsQuantityFromMessage();
        int productMiniaturesQuantity = getProductsGridsPage().getProductsQuantity();
        if(!checkIfQuantityOfProductsInMessageIsCorrect()){
            listToAddBug.add("Should be " + productQuantityInMessage + " products, found {}" +productMiniaturesQuantity);
            logger.error("Should be " + productQuantityInMessage + " products, found {}" +productMiniaturesQuantity);
            return new CategoryPage(driver);
        }
        logger.info("Quantity of products equal");
        return new CategoryPage(driver);
    }

    public CategoryPage addMessageToListIfMenuNotDisplayed(List<String> listToAddBug, WebElement categoryName) {
        if (!isFilterMenuDisplayed()) {
            listToAddBug.add("Filter menu is not displayed in " + categoryName.getText());
        }
        return new CategoryPage(driver);
    }

    public CategoryPage addMessageToListIfTitlesNotEquals(List<String> listToAddBug, WebElement elementWithTitle){
        String titleFromCategoryBox = getCategoryBoxHeader().getText();
        String titleFromMenu = elementWithTitle.getText();
        if(!titleFromMenu.equals(titleFromCategoryBox)){
            listToAddBug.add("Title on categoryBox is " + titleFromCategoryBox + ", menu title is " + titleFromMenu);
            logger.error("Title on categoryBox is " + titleFromCategoryBox + ", menu title is " + titleFromMenu);
            return new CategoryPage(driver);
        }
        logger.info("Titles are Equal");
        return new CategoryPage(driver);
    }
}
