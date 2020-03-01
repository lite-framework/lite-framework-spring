package cc.lite.framework.common;

import cc.lite.framework.type.UserType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseRequest extends CommonVO implements UserType {
    private Long userId;
}
