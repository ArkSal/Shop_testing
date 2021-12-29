package models.basket;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class BasketLine {
    private Logger logger = LoggerFactory.getLogger(BasketLine.class);
    private Product product;
    private int quantity;
    private BigDecimal totalSum;

    public BasketLine(String name, BigDecimal price, int quantity) {
        this.product = new Product(name, price);
        this.quantity = quantity;
        this.totalSum = getTotalSum();
    }

    public BigDecimal getTotalSum(){
        return product.getPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
    }

   public String getProductName(){
        return product.getName();
   }

   public void setQuantityAndCalculateTotalSum(int quantity){
        this.quantity += quantity;
        totalSum = getTotalSum();
   }


}
