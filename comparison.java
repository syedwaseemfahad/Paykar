import java.math.BigDecimal;

public class ObjectValueComparator {
    public static boolean objectsHaveSameValue(Object newValue, Object oldValue, int numericPrecision) {
        if (isNumeric(newValue) && isNumeric(oldValue)) {
            // Compare numeric objects with the specified precision using BigDecimal
            BigDecimal bd1 = toBigDecimal(newValue).setScale(numericPrecision, BigDecimal.ROUND_HALF_UP);
            BigDecimal bd2 = toBigDecimal(oldValue).setScale(numericPrecision, BigDecimal.ROUND_HALF_UP);

            return bd1.compareTo(bd2) == 0;
        } else if (isString(newValue) && isString(oldValue)) {
            // For string objects, compare after removing whitespace and trailing zeros
            String str1 = removeWhitespace(newValue.toString()).replaceAll("\\.0+$", "");
            String str2 = removeWhitespace(oldValue.toString()).replaceAll("\\.0+$", "");
            return str1.equals(str2);
        } else {
            // For non-numeric and non-string objects, compare them as strings
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
        Object oldValue = 10.1234567;  // Another double
        Object obj3 = "10.1234500"; // A numeric string with extra zeros
        Object obj4 = "Hello";      // A string with whitespace

        int precision = 3;

        System.out.println("newValue and oldValue have the same value with precision " + precision + ": " +
                objectsHaveSameValue(newValue, oldValue, precision)); // true
        System.out.println("newValue and obj3 have the same value with precision " + precision + ": " +
                objectsHaveSameValue(newValue, obj3, precision)); // true (ignoring extra zeros)
        System.out.println("newValue and obj4 have the same value with precision " + precision + ": " +
                objectsHaveSameValue(newValue, obj4, precision)); // false
    }
}
