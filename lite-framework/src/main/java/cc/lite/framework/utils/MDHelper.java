package cc.lite.framework.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MDHelper {
    public static String md5(String originString) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(originString.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
