package models;

import base.factory.AddressFactory;
import base.factory.UserFactory;
import models.address.Address;
import models.basket.Basket;
import models.user.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import pages.accounts.OrderHistoryLinePage;
import pages.accounts.SingleOrderDetailsPage;
import pages.basket.OrderConfirmationPage;
import pages.basket.PaymentPage;
import pages.basket.ShoppingCartPage;
import pages.common.HeaderPage;
import pages.products.ProductAddedToBasketPopupPage;
import providers.RandomDataGenerator;

public class BasketAndCheckoutTests extends BaseTest{

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

    @RepeatedTest(10)
    void basketOperationsTest(){
        softly = new SoftAssertions();
        basket = new Basket();
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        HeaderPage headerPage = application.open()
                .getHeaderPage();

        for (int i = 0; i < 5; i++) {
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

    @Test
    void registrationAndCheckoutTest(){
        basket = new Basket();
        User newRandomCreatedUser = new UserFactory().getRandomUser();
        SoftAssertions softly = new SoftAssertions();
        String createdAccountName =
                application.open()
                        .getHeaderPage()
                        .clickSignInButton()
                        .goToNewAccountCreation()
                        .fillFormWithNewUserData(newRandomCreatedUser)
                        .clickSaveNewUSerButton()
                        .getHeaderPage()
                        .getLoggerAccountFullName();

        softly.assertThat(createdAccountName).isEqualTo((newRandomCreatedUser.getUserFullName()));

        Address address = new AddressFactory().getRandomizedRequiredAddressFields();
        PaymentPage paymentPage=
        application.open()
                .addRandomProductToBasket(5,new RandomDataGenerator().getRandomNumberInRangeMinMax(1,3), basket)
                .clickProceedToCheckoutButton()
                .fillAndSaveRequiredFieldsWithRandomizedPolishData(address, newRandomCreatedUser)
                .clickContinueButton()
                .clickOnContinueButton()
                .pickPayByBankWireOption()
                .openTermsOfServicePopup();
        softly.assertThat(paymentPage.checkIfTermsPopupHasText()).isEqualTo(true);

        OrderConfirmationPage orderConfirmationPage =
        paymentPage
                .closeRulesPopup()
                .acceptTerms()
                .clickPlaceOrderButton();
        softly.assertThat(basket).usingRecursiveComparison().isEqualTo(orderConfirmationPage.readBasketFromConfirmationPage());
        softly.assertThat(orderConfirmationPage.getPaymentMethod()).isEqualTo(environmentConfig.getPaymentMethodName());
        softly.assertThat(orderConfirmationPage.getShippingMethod()).contains(environmentConfig.getShippingMethodName());

        String orderReferenceNumber = orderConfirmationPage.getOrderReferenceNumber();
        OrderHistoryLinePage orderHistoryLinePage =
        orderConfirmationPage.goToMyAccountPage()
                        .clickOrderHistoryAndDetailsIcon()
                                .getOrderHistoryLineByReferenceNumber(orderReferenceNumber);
        softly.assertThat(orderHistoryLinePage.getTotalPrice()).isEqualTo(basket.getTotalSumInBasket());
        softly.assertThat(orderHistoryLinePage.getPaymentText()).isEqualTo(environmentConfig.getPaymentMethodName());
        softly.assertThat(orderHistoryLinePage.getStatusText()).isEqualTo(environmentConfig.getOrderStatus());
        softly.assertThat((orderHistoryLinePage.isDateEqualsToday())).isEqualTo(true);

        SingleOrderDetailsPage singleOrderDetailsPage =
                orderHistoryLinePage.clickOnDetailsIcon();
        softly.assertThat(singleOrderDetailsPage.getBasketFromOrderHistoryPage()).usingRecursiveComparison().isEqualTo(basket);
        softly.assertThat(address).usingRecursiveComparison().isEqualTo(singleOrderDetailsPage.readDeliveryAddress());
        softly.assertThat(address).usingRecursiveComparison().isEqualTo(singleOrderDetailsPage.readInvoiceAddress());
        softly.assertAll();
    }

}
