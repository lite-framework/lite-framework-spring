package io.lite.framework.common;

import io.lite.framework.type.UserType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;


/**
 * 前段页面请求的基类
 */
@Data
@Accessors(chain = true)
public class PortalRequest extends PageRequest implements UserType {

    private static final long serialVersionUID = 1L;

    private Long userId;

    // 浏览器端
    private Map<String, String> cookies;
    private String userAgent;


    // app 端
    private String appVersion;
    private Boolean isFromApp; //app 会将此标记标识为true
    private String platform;


    // rules portal 上传递过来的翻页请求，最多响应200大的size
    @Override
    public PortalRequest setSize(Integer size) {
        super.setSize(size > MAX_ITEMS ? MAX_ITEMS : size);
        return this;
    }

}
