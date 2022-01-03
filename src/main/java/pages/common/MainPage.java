package pages.common;

import lombok.Getter;
import models.basket.Basket;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import pages.products.ProductAddedToBasketPopupPage;
import pages.products.ProductsGridsPage;
import pages.basket.ShoppingCartPage;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MainPage extends BasePage {
    private HeaderPage headerPage;
    private ProductsGridsPage productsGridsPage;
    private FooterPage footerPage;

    @FindBy(css = "[role='listbox'] li")
    private List<WebElement> listBoxItems;

    @FindBy(css = "[data-slide='prev']")
    private WebElement previousListBoxItem;

    @FindBy(css = "[data-slide='next']")
    private WebElement nextListBoxItem;

    @FindBy(css = ".banner")
    private WebElement priceDropBanner;

    public MainPage(WebDriver driver) {
        super(driver);
        this.headerPage = new HeaderPage(driver);
        this.productsGridsPage = new ProductsGridsPage(driver);
        this.footerPage = new FooterPage(driver);
    }

    public String getRandomProductNameFromMiniatures() {
        return getProductsGridsPage()
                .getRandomProductName();
    }

    public boolean isRandomProductFoundInTextField() {
        String randomTitle = getRandomProductNameFromMiniatures();
        return headerPage.sendKeysToSearchTextField(randomTitle)
                .clickSearchButton()
                .checkIfProductExistInGrid(randomTitle);
    }

    public List<String> getListOfInconsistenciesWhenIteratingCategories() {
        List<String> differencesList = new ArrayList<>();
        for (int i = 0; i < headerPage.getCategoriesList().size(); i++) {
            String categoryName = headerPage.getCategoriesList().get(i).getText();
            clickOnElement(headerPage.getCategoriesList().get(i));

            new CategoryPage(driver).addMessageToListIfQuantityOfProductNotEqual(differencesList)
                    .addMessageToListIfMenuNotDisplayed(differencesList, headerPage.getCategoriesList().get(i))
                    .addMessageToListIfTitlesNotEquals(differencesList, categoryName);
        }
        return differencesList;
    }

    public List<String> getListOfInconsistenciesWhenIteratingSubcategories() {
        List<String> differencesList = new ArrayList<>();
        for (int i = 0; i < headerPage.getCategoriesList().size(); i++) {
            List<WebElement> subcategoriesList = headerPage.getCategoriesList().get(i).findElements(By.cssSelector(".category>a[data-depth='1']"));
            for (int j = 0; j < subcategoriesList.size(); j++) {
                actions.moveToElement(headerPage.getCategoriesList().get(i)).build().perform();
                String categoryName = headerPage.getCategoriesList().get(i).
                        findElements(By.cssSelector(".category>a[data-depth='1']")).get(j).getText();
                clickOnElement(headerPage.getCategoriesList().get(i).
                        findElements(By.cssSelector(".category>a[data-depth='1']")).get(j));

                new CategoryPage(driver).addMessageToListIfQuantityOfProductNotEqual(differencesList)
                        .addMessageToListIfMenuNotDisplayed(differencesList, headerPage.getCategoriesList().get(i).
                                findElements(By.cssSelector(".category>a[data-depth='1']")).get(j))
                        .addMessageToListIfTitlesNotEquals(differencesList, categoryName);
            }
        }
        return differencesList;
    }

    public ShoppingCartPage addRandomProductToBasket(int amountOfProductsToAdd, int quantitySet, Basket basket){
        for (int i = 0; i < amountOfProductsToAdd; i++) {
            ProductAddedToBasketPopupPage popupPage =
                    headerPage.clickRandomCategory()
                    .clickOnRandomProductMiniature()
                    .setRandomProductQuantityInRange(quantitySet)
                    .addProductToDatabase(basket)
                    .clickAddToCartButton();
            if(i==(amountOfProductsToAdd-1)){
                popupPage.clickProceedToCheckout();
            }
            else{
                popupPage.clickContinueShopping();
            }
        }
        return new ShoppingCartPage(driver);
    }


}