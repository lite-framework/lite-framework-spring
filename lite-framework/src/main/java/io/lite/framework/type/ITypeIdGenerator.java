package io.lite.framework.type;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface ITypeIdGenerator {

    @RequestMapping(value = "/nextId", method = RequestMethod.POST)
    long nextId(String type);
}
