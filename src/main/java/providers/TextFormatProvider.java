package providers;

import java.math.BigDecimal;

public class TextFormatProvider {

    public static int getIntFromString(String text){
        return Integer.parseInt(text.replaceAll("[\\D]", ""));
    }

    public static BigDecimal getBigDecimalFromStringWithCurrency(String text){
        return new BigDecimal(text.replace("$", ""));
    }
}
