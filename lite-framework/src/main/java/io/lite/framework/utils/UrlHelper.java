package io.lite.framework.utils;

import org.apache.commons.lang3.StringUtils;

public class UrlHelper {

    public static String getParamValue(String url, String param) {
        String part = StringUtils.substringAfterLast(url, param + "=");
        return StringUtils.substringBefore(part, "&");
    }


    public static String setParamValue(String url, String param, String value) {
        String oldValue = getParamValue(url, param);
        return StringUtils.replace(url, param + "=" + oldValue, param + "=" + value);
    }

    public static String incrParamValue(String url, String param) {
        String oldValue = getParamValue(url, param);
        Long newValue = Long.valueOf(oldValue) + 1;
        return StringUtils.replace(url, param + "=" + oldValue, param + "=" + newValue);
    }


    public static void main(String[] args) {
        String url = "https://color.jd.id/m.jd.id/search/1.0?category=875061695&page=0&pagesize=60&sdfsdf=5555&ert=7777";
        System.out.println(getParamValue(url, "category"));
        System.out.println(getParamValue(url, "ert"));
        System.out.println(getParamValue(url, "page"));
        System.out.println(getParamValue(url, "pagesize"));

        System.out.println(incrParamValue(url, "ert"));
    }
}
