package io.lite.framework.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionHelper {

    public static Map map(Object key, Object value) {
        Map map = new HashMap();
        map.put(key, value);
        return map;
    }

    public static Map map(Object... ojbs) {
        Map map = new HashMap();
        for (int i = 0; i < ojbs.length; i = i + 2) {
            map.put(ojbs[i], ojbs[i + 1]);
        }
        return map;
    }

    public static List list() {
        return new ArrayList();
    }


    public static List list(Object... ojbs) {
        List list = list();
        for (int i = 0; i < ojbs.length; i++) {
            list.add(ojbs[i]);
        }
        return list;
    }
}
