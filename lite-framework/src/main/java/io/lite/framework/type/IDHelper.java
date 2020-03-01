package io.lite.framework.type;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class IDHelper {
    public static Date id2date(Long id) {
        return new Date(id / 1_00_000);
    }


    public static String id2dateStr(Long id) {
        return id2DateStr(id, "YYYYMMddHHmmss");
    }

    public static String id2DateStr(Long id, String format) {
        return DateFormatUtils.format(id2date(id), format);
    }


}
