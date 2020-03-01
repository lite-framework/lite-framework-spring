package io.lite.framework.common;

import io.lite.framework.type.UserType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Request extends VO implements UserType {
    private Long userId;
}
