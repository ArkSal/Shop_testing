package models;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasketAndCheckoutTests extends BaseTest{
    private Logger logger = LoggerFactory.getLogger(BasketAndCheckoutTests.class);

    @Test
    void productAddingToShoppingCartTest(){
        application.open()
                .getHeaderPage()
                .clickRandomCategory()
                .clickOnRandomProductMiniature()
                .setRandomProductQuantityInRange(5);


    }

}
