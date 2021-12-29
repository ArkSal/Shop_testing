package models.basket;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Product {
    private String name;
    private BigDecimal price;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }


}
