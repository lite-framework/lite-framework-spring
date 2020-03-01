package io.lite.framework.utils;

import io.lite.framework.common.BizError;
import io.lite.framework.common.ResponseCode;
import io.lite.framework.type.IResponseCode;

public class ErrorHelper {
    public static void err(IResponseCode responseCode) {
        throw new BizError(responseCode);
    }

    public static void errIf(boolean cond, IResponseCode responseCode) {
        if (cond)
            err(responseCode);
    }


    public static void errIf(boolean cond, String message) {
        if (cond)
            err(new IResponseCode() {
                @Override
                public String getCode() {
                    return ResponseCode.SYSTEM_ERROR.getCode();
                }

                @Override
                public String getMessage() {
                    return message;
                }
            });
    }


    public static void paramErr(String message) {
        err(new IResponseCode() {
            @Override
            public String getCode() {
                return ResponseCode.PARAM_ERROR.getCode();
            }

            @Override
            public String getMessage() {
                return message;
            }
        });
    }

    public static void paramErrIf(boolean cond, String message) {
        if (cond)
            paramErr(message);
    }
}
