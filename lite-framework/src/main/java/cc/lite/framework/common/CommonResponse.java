package cc.lite.framework.common;


import cc.lite.framework.type.IResponseCode;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommonResponse<T> extends CommonVO {

    private static final long serialVersionUID = 1L;

    private String code = CommonResponseCode.SUCCESS.getCode();
    private String message;
    private T data;

    private String traceId;


    public static CommonResponse of(IResponseCode responseCode) {
        return new CommonResponse().setCode(responseCode.getCode()).setMessage(responseCode.getMessage());
    }


    public Boolean ok() {
        return this.code != null && this.code.equals(CommonResponseCode.SUCCESS.getCode());
    }

    public Boolean idem() {
        return this.code != null && this.code.equals(CommonResponseCode.DUP_REQUEST.getCode());
    }

    public CommonResponse<T> checkThrow() {
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
