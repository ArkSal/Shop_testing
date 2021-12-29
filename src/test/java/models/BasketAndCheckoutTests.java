package models;

import models.basket.Basket;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.HeaderPage;
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
            ShoppingCartPage shoppingCartPage =
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

    @Test
    void testsad(){


    }

}
