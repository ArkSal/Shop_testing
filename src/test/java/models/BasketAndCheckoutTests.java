package models;

import models.basket.Basket;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.HeaderPage;
import pages.ProductAddedToBasketPopupPage;
import pages.ShoppingCartPage;

public class BasketAndCheckoutTests extends BaseTest{
    private Logger logger = LoggerFactory.getLogger(BasketAndCheckoutTests.class);

    @Test
    void productAddingToShoppingCartTest(){
        softly = new SoftAssertions();
        basket = new Basket();
        HeaderPage headerPage =
        application.open()
                .getHeaderPage();

        for (int i = 0; i < 4; i++) {
            ProductAddedToBasketPopupPage shoppingCartPage =
                    headerPage.clickRandomCategory()
                    .clickOnRandomProductMiniature()
                    .setRandomProductQuantityInRange(environmentConfig.getProductsQuantityRange())
                    .addProductToDatabase(basket)
                    .clickAddToCartButton();
            softly.assertThat(shoppingCartPage.isPopupHasCorrectValues(basket)).isEqualTo(true);
            shoppingCartPage.clickContinueShopping();
            softly.assertThat(headerPage.isQuantityInCartIconCorrect(basket)).isEqualTo(true);
        }
        softly.assertAll();
    }

    @Test()
    void basketOperationsTest(){
        softly = new SoftAssertions();
        basket = new Basket();
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        HeaderPage headerPage = application.open()
                .getHeaderPage();

        for (int i = 0; i < 2; i++) {
            headerPage.clickRandomCategory()
                    .clickOnRandomProductMiniature()
                    .addProductToDatabase(basket)
                    .clickAddToCartButton()
                    .clickContinueShopping();
        }

        Basket basketFromCart = headerPage.clickBasketIcon().getBasketFromShoppingCart();
        softly.assertThat(basket).usingRecursiveComparison().isEqualTo(basketFromCart);
        softly.assertThat(basket.getTotalSumInBasket()).isEqualTo(basketFromCart.getTotalSumInBasket());

        shoppingCartPage.setQuantityOfProduct(basket,
                environmentConfig.getIndexOfProductToSetQuantity(), environmentConfig.getQuantityToSetInProduct());
        softly.assertThat(basket).usingRecursiveComparison().isEqualTo(shoppingCartPage.getBasketFromShoppingCart());

        shoppingCartPage.increaseQuantityByClickingArrowUp(1, basket, 0);
        softly.assertThat(basket).usingRecursiveComparison().isEqualTo(shoppingCartPage.getBasketFromShoppingCart());

        shoppingCartPage.decreaseQuantityByClickingArrowUp(1, basket, 0);
        softly.assertThat(basket).usingRecursiveComparison().isEqualTo(shoppingCartPage.getBasketFromShoppingCart());

        int size = basket.getProductsInBasket().size();
        for (int i = 0; i < size; i++) {
            shoppingCartPage.deleteProductFromBasket(0, basket);
            softly.assertThat(basket.getTotalSumInBasket()).isEqualTo(shoppingCartPage.getBasketFromShoppingCart().getTotalSumInBasket());
        }


        softly.assertAll();


    }

}
