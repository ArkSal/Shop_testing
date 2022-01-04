package pages.basket;

import models.basket.Basket;
import models.basket.BasketLine;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;
import providers.TextFormatProvider;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartPage extends BasePage {
    Logger logger = LoggerFactory.getLogger(ShoppingCartPage.class);
    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".checkout .btn-primary")
    private WebElement proceedToCheckoutButton;

    @FindBy(css = ".cart-total .value")
    private WebElement totalPriceValue;

    @FindBy(css = ".cart-item")
    private List<WebElement> productsList;

    @FindBy(css = ".js-subtotal")
    private WebElement itemsQuantityMessage;

    public List<ShoppingCartProductPage> createProductsList() {
        List<ShoppingCartProductPage> productsShoppingCartList = new ArrayList<>();
        for (WebElement product : productsList) {
            productsShoppingCartList.add(new ShoppingCartProductPage(product, driver));
        }
        logger.info("List of products in shopping cart created");
        return productsShoppingCartList;
    }

    public Basket getBasketFromShoppingCart(){
        Basket shoppingCartBasket = new Basket();
        for (ShoppingCartProductPage shoppingCartProductPage : createProductsList()) {
            shoppingCartBasket.addProductLineToBasket(shoppingCartProductPage.getProductFromCart());
        }
        return shoppingCartBasket;
    }

    public ShoppingCartPage setQuantityOfProduct(Basket basket, int indexOfProductToSet, int quantityToSet){
        int quantityBeforeChange = basket.getTotalQuantity();
        int differenceInSingleProductQuantity = quantityToSet-basket.getProductsInBasket().get(indexOfProductToSet).getQuantity();
        sendKeysToElement(createProductsList().get(indexOfProductToSet).getSingleProductQuantityField(), String.valueOf(quantityToSet));
        actions.sendKeys(Keys.ENTER).build().perform();
        waitUntilQuantityMessageIsUpdated(quantityBeforeChange, differenceInSingleProductQuantity);
        BasketLine basketLine = basket.getProductsInBasket().get(indexOfProductToSet);
        basketLine.setQuantityAndCalculateTotalSum(quantityToSet);
        logger.info("{} product quantity set to: {}", basketLine.getProductName(), quantityToSet);
        return this;
    }

    public ShoppingCartPage increaseQuantityByClickingArrowUp(int amountOfClicks, Basket basket, int indexOfProductToIncrease){
        int quantityBeforeChange = basket.getTotalQuantity();
        for (int i = 0; i < amountOfClicks; i++) {
            clickOnElement(createProductsList().get(indexOfProductToIncrease).getQuantityIncreaseButton());
            logger.info("Quantity increased");
            waitUntilQuantityMessageIsUpdated(quantityBeforeChange, 1);
        }
        basket.getProductsInBasket().get(indexOfProductToIncrease).increaseQuantity(amountOfClicks);
        return this;
    }

    public ShoppingCartPage decreaseQuantityByClickingArrowUp(int amountOfClicks, Basket basket, int indexOfProductToDecrease){
        int quantityBeforeChange = basket.getTotalQuantity();
        for (int i = 0; i < amountOfClicks; i++) {
            clickOnElement(createProductsList().get(indexOfProductToDecrease).getQuantityDecreaseButton());
            logger.info("Quantity decreased");
            waitUntilQuantityMessageIsUpdated(quantityBeforeChange, -1);
        }
        basket.getProductsInBasket().get(indexOfProductToDecrease).decreaseQuantity(amountOfClicks);
        return this;
    }

    public ShoppingCartPage deleteProductFromBasket(int indexOfProduct, Basket basket){
        String productName = basket.getProductsInBasket().get(indexOfProduct).getProductName();
        clickOnElement(createProductsList().get(indexOfProduct).getRemoveItemFromBasketIcon());
        basket.getProductsInBasket().remove(indexOfProduct);
        int actualQuantity = basket.getTotalQuantity();
        waitUntilQuantityMessageIsUpdated(actualQuantity);
        logger.info("Deleted {} from basket", productName);
        return this;
    }

    public int getTotalQuantity() {
        return TextFormatProvider.getIntFromString((itemsQuantityMessage.getText()));
    }

    public void waitUntilQuantityMessageIsUpdated(int previousQuantity, int increase) {
        wait.until(x -> getTotalQuantity()==(previousQuantity + increase));
    }

    public void waitUntilQuantityMessageIsUpdated(int actualQuantity) {
        wait.until(x -> getTotalQuantity()==actualQuantity);
    }

    public AddressesPage clickProceedToCheckoutButton(){
        String buttonName = proceedToCheckoutButton.getText();
        clickOnElement(proceedToCheckoutButton);
        logger.info("{} button clicked", buttonName);
        return new AddressesPage(driver);
    }




}
