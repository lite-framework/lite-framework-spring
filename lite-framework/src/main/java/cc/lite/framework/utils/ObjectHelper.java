package cc.lite.framework.utils;

import cc.lite.framework.common.BizError;
import cc.lite.framework.common.CommonResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
public class ObjectHelper {
    public static <T> T convert(Object o, Class<T> clazz) {
        try {

            T instance = clazz.newInstance();
            BeanUtils.copyProperties(o, instance);
            return instance;
        } catch (Exception t) {
            log.error("copy_err", t);
            throw new BizError(CommonResponseCode.SYSTEM_ERROR);
        }
    }

}
