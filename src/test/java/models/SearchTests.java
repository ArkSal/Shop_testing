package models;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SearchTests extends BaseTest{

    @Test
    void validateIfProductInSearchResultsEqualsRandomProductFromMiniatures(){
        boolean isProductFoundInSearchField =
                application.open()
                        .isRandomProductFoundInTextField();

        assertThat(isProductFoundInSearchField, equalTo(true));
    }

    @Test
    void validateIfProductInDropdownResultsEqualsRandomProductFromMiniatures(){
        String productNameToSearch =
                application.open()
                        .getProductsGridsPage()
                        .getRandomProductName();

        boolean isProductFoundInSearchField =
                application.open()
                .getHeaderPage()
                .sendKeysToSearchTextField(productNameToSearch)
                .checkIfProductExistInSearchField(productNameToSearch);

        assertThat(isProductFoundInSearchField, equalTo(true));
    }


}
