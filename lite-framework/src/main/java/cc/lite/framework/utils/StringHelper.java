package cc.lite.framework.utils;


import org.apache.commons.lang3.StringUtils;

public class StringHelper {
    public static String removeNonNumeric(String str) {
        return str.replaceAll("[^\\d]", "");
    }

    public static String removeNonNumericExDot(String str) {
        if (str.indexOf(".") > 0) {

            return removeNonNumeric(StringUtils.substringBeforeLast(str, ".")) + "." + removeNonNumeric(
                    StringUtils.substringAfterLast(str, ".")
            );
        } else {
            return removeNonNumeric(str);
        }
    }

    public static String tight(String str) {
        return str.trim().replaceAll(" +", " ");
    }

}
