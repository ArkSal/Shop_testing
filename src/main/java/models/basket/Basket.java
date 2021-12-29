package models.basket;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Basket {
    private Logger logger = LoggerFactory.getLogger(Basket.class);

    private List<BasketLine> productsInBasket;

    public Basket() {
        this.productsInBasket = new ArrayList<>();
    }

    public void addProductLineToBasket(String name, BigDecimal price, int quantity){
        int index = getIndexOfProductIfAlreadyInBasket(name);
        if(index<0){
            productsInBasket.add(new BasketLine(name, price, quantity));
            logger.info("Product {} added to basket", name);
            return;
        }
        productsInBasket.get(index).setQuantityAndCalculateTotalSum(quantity);
        logger.info("Product {} added to basket", name);
    }

    private int getIndexOfProductIfAlreadyInBasket(String name){
        for (BasketLine basketLine : productsInBasket) {
            if(basketLine.getProductName().equals(name)){
                logger.info("Product already added to basket");
                return productsInBasket.indexOf(basketLine);
            }
        }
        return -1;
    }

    public BasketLine getBasketLine(String name){
        int index = getIndexOfProductIfAlreadyInBasket(name);
        if (index<0){
            logger.info("No product in basket");
            return null;
        }
        return productsInBasket.get(index);
    }

    public BigDecimal getTotalSumInBasket(){
        return productsInBasket.stream()
                .map(BasketLine::getTotalSum)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getTotalQuantity(){
        return productsInBasket.stream()
                .mapToInt(BasketLine::getQuantity)
                .sum();
    }
}
