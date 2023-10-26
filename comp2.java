import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ObjectValueComparator {
    public static boolean objectsHaveSameValue(Object newValue, Object oldValue, int numericPrecision) {
        if (isNumeric(newValue) && isNumeric(oldValue)) {
            BigDecimal bd1 = toBigDecimal(newValue).setScale(numericPrecision, BigDecimal.ROUND_HALF_UP);
            BigDecimal bd2 = toBigDecimal(oldValue).setScale(numericPrecision, BigDecimal.ROUND_HALF_UP);
            return bd1.compareTo(bd2) == 0;
        } else if (isString(newValue) && isString(oldValue)) {
            String str1 = removeWhitespace(newValue.toString()).replaceAll("\\.0+$", "");
            String str2 = removeWhitespace(oldValue.toString()).replaceAll("\\.0+$", "");
            return str1.equals(str2);
        } else if (isDate(newValue) && isDate(oldValue)) {
            return newValue.equals(oldValue);
        } else {
            String str1 = newValue.toString();
            String str2 = oldValue.toString();
            return str1.equals(str2);
        }
    }

    private static boolean isNumeric(Object obj) {
        return obj instanceof Number || obj instanceof String && isNumericString((String) obj);
    }

    private static boolean isString(Object obj) {
        return obj instanceof String;
    }

    private static boolean isDate(Object obj) {
        return obj instanceof Date;
    }

    private static boolean isNumericString(String str) {
        try {
            new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static BigDecimal toBigDecimal(Object obj) {
        if (obj instanceof Number) {
            Number number = (Number) obj;
            return new BigDecimal(number.toString());
        } else if (obj instanceof String) {
            return new BigDecimal((String) obj);
        }
        return null;
    }

    private static String removeWhitespace(String str) {
        return str.replaceAll("\\s", "");
    }

    public static void main(String[] args) {
        Object newValue = 10.12345;     // A double
        Object floatValue = 10.1234567f; // A float
        Object stringValue1 = "10.1234500"; // A numeric string with extra zeros
        Object stringValue2 = "Hello";      // A string with whitespace

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateValue1, dateValue2;

        try {
            dateValue1 = dateFormat.parse("2023-10-26");
            dateValue2 = dateFormat.parse("2023-10-27");
        } catch (ParseException e) {
            dateValue1 = dateValue2 = null;
        }

        int precision = 3;

        System.out.println("newValue and floatValue have the same value with precision " + precision + ": " +
                objectsHaveSameValue(newValue, floatValue, precision)); // true
        System.out.println("newValue and stringValue1 have the same value with precision " + precision + ": " +
                objectsHaveSameValue(newValue, stringValue1, precision)); // true (ignoring extra zeros)
        System.out.println("newValue and stringValue2 have the same value with precision " + precision + ": " +
                objectsHaveSameValue(newValue, stringValue2, precision)); // false

        System.out.println("dateValue1 and dateValue2 have the same value: " +
                objectsHaveSameValue(dateValue1, dateValue2, precision)); // false
    }
}
