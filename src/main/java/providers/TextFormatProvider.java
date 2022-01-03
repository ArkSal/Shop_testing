package providers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TextFormatProvider {

    public static int getIntFromString(String text){
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }

    public static BigDecimal getBigDecimalFromStringWithCurrency(String text){
        return new BigDecimal(text.replace("$", ""));
    }

    public static LocalDate getLocalDateFromString(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(date, formatter);
    }
}
