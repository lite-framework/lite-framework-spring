package io.lite.framework.common;


import io.lite.framework.type.IResponseCode;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Response<T> extends VO {

    private static final long serialVersionUID = 1L;

    private String code = ResponseCode.SUCCESS.getCode();
    private String message;
    private T data;

    private String traceId;


    public static Response of(IResponseCode responseCode) {
        return new Response().setCode(responseCode.getCode()).setMessage(responseCode.getMessage());
    }


    public Boolean ok() {
        return this.code != null && this.code.equals(ResponseCode.SUCCESS.getCode());
    }

    public Boolean idem() {
        return this.code != null && this.code.equals(ResponseCode.DUP_REQUEST.getCode());
    }

    public Response<T> checkThrow() {
        if (!ok())
            throw new BizError(new IResponseCode() {
                @Override
                public String getCode() {
                    return code;
                }

                @Override
                public String getMessage() {
                    return message;
                }
            });

        return this;
    }

    public T checkGet() {
        checkThrow();
        return data;
    }
}
