package io.lite.framework.helper;

import io.lite.framework.common.BizError;
import io.lite.framework.common.ResponseCode;
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
            throw new BizError(ResponseCode.SYSTEM_ERROR);
        }
    }

}
