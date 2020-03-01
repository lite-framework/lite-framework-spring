package io.lite.framework.common;

import io.lite.framework.type.Expirable;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ExpirableMessage implements Expirable, Serializable {

    private Serializable data;

    private Long expireAt;

    @Override
    public boolean expired() {
        return System.currentTimeMillis() > expireAt;
    }

    public static ExpirableMessage of(Serializable data, int second) {
        return new ExpirableMessage().setData(data).setExpireAt(System.currentTimeMillis() + second * 1000);
    }
}
