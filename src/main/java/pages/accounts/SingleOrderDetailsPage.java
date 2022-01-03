package pages.accounts;

import models.address.Address;
import models.basket.Basket;
import models.basket.BasketLine;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import providers.TextFormatProvider;

import java.util.List;

public class SingleOrderDetailsPage extends BasePage {
    public SingleOrderDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#order-products strong")
    private List<WebElement> productsNamesList;

    @FindBy(css = "#order-products tbody td:nth-of-type(2)")
    private List<WebElement> productsQuantityList;

    @FindBy(css = "#order-products td:nth-of-type(3)")
    private List<WebElement> singleProductPrice;

    @FindBy(css = "#order-products td:nth-of-type(4)")
    private List<WebElement> singleProductTotalPrice;

    @FindBy(css = ".addresses address")
    private List<WebElement> fullAddressText;

    public Basket getBasketFromOrderHistoryPage(){
        Basket basket = new Basket();;
        for (int i = 0; i < productsNamesList.size(); i++) {
            basket.addProductLineToBasket(new BasketLine(StringUtils.substringBefore(productsNamesList.get(i).getText()," -"),
                    TextFormatProvider.getBigDecimalFromStringWithCurrency(singleProductPrice.get(i).getText()),
                    TextFormatProvider.getIntFromString(productsQuantityList.get(i).getText()),
                    TextFormatProvider.getBigDecimalFromStringWithCurrency(singleProductTotalPrice.get(i).getText())));
        }
        return basket;
    }

    public Address readDeliveryAddress(){
        String[] splitAddress = fullAddressText.get(0).getText().split("\n");
        String[] fullNameSplit = splitAddress[0].split(" ");
        String[] postalAndCity = splitAddress[2].split(" ",2);
        return Address.builder()
                .firstName(fullNameSplit[0])
                .lastName(fullNameSplit[1])
                .address(splitAddress[1])
                .city(postalAndCity[1])
                .postalCode(postalAndCity[0])
                .country(splitAddress[3])
                .build();
    }
    public Address readInvoiceAddress(){
        String[] splitAddress = fullAddressText.get(1).getText().split("\n");
        String[] fullNameSplit = splitAddress[0].split(" ");
        String[] postalAndCity = splitAddress[2].split(" ", 2);
        return Address.builder()
                .firstName(fullNameSplit[0])
                .lastName(fullNameSplit[1])
                .address(splitAddress[1])
                .city(postalAndCity[1])
                .postalCode(postalAndCity[0])
                .country(splitAddress[3])
                .build();
    }
}
