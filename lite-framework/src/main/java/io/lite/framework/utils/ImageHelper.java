package io.lite.framework.utils;


import io.lite.framework.type.IDHelper;
import org.apache.commons.lang3.StringUtils;


/**
 * rules 按照图片id 的时间元素（去掉后5位)转化为时间因素后，生成日期时间格式目录
 */
public class ImageHelper {
    public static String id2path(Long imageId, String fix) {

        String _fix = StringUtils.isBlank(fix) ? "jpg" : fix;
        return IDHelper.id2DateStr(imageId, "/YYYY/MM/dd/HH/") + imageId + "." + _fix;
    }


    public static String id2path(Long imageId) {
        return id2path(imageId, "jpg");
    }
}
