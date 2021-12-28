package models;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.OnSalePage;
import pages.ProductDetailsPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ProductsAndCategoriesTests extends BaseTest{
    private Logger logger = LoggerFactory.getLogger(ProductsAndCategoriesTests.class);

    @Test
    void iterateThoughCategoriesAndCheckIncompatibilities(){
        List<String> listWithDifferences =
        application.open()
                .getListOfInconsistenciesWhenIteratingCategories();
        logger.info("List with differences created");

        assertThat(listWithDifferences.isEmpty(), is(true));
    }

    @Test
    void iterateThoughSubcategoriesAndCheckIncompatibilities(){
        List<String> listWithDifferences =
                application.open()
                        .getListOfInconsistenciesWhenIteratingSubcategories();
        logger.info("List with differences created");

        assertThat(listWithDifferences.isEmpty(), is(true));
    }


    @Test
    void validateIFPriceDropWorksCorrectly(){
        SoftAssertions softly = new SoftAssertions();
        OnSalePage onSalePage =
                application.open()
                        .getFooterPage()
                        .goToOnSalePage();

        softly.assertThat(onSalePage.isOnSaleTitleDisplayed()).isEqualTo(true);
        softly.assertThat(onSalePage.isPriceDropDisplayedOnEveryProduct(environmentConfig.getPriceDrop())).isEqualTo(true);
        softly.assertThat(onSalePage.isRegularAndDiscountPriceDisplayedOnEveryProduct()).isEqualTo(true);
        softly.assertThat(onSalePage.isEveryDiscountPriceCalculatedProperly()).isEqualTo(true);

        ProductDetailsPage productDetailsPage =
                onSalePage.getProductsGridsPage().clickOnRandomProductMiniature();
        softly.assertThat(productDetailsPage.isDiscountDisplayed()).isEqualTo(true);
        softly.assertThat(productDetailsPage.isRegularPriceDisplayed()).isEqualTo(true);
        softly.assertThat(productDetailsPage.isPriceAfterDiscountDisplayed()).isEqualTo(true);
        softly.assertThat(productDetailsPage.isPriceCalculatedCorrectly()).isEqualTo(true);
        softly.assertAll();


    }
}
