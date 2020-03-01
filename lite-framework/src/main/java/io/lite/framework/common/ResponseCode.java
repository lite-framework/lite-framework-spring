package io.lite.framework.common;


import io.lite.framework.Def;
import io.lite.framework.type.IResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode implements IResponseCode {


    // biz
    SUCCESS("success", ""),
    ACCEPTED("accepted", ""),
    DUP_REQUEST("dup_request", ""),
    PARAM_ERROR(Def.ERR_MARK + "param", ""),
    REQUEST_RATE_LIMITED(Def.ERR_MARK + "request_limited", ""),


    // system error
    SYSTEM_ERROR(ResponseCode.ERROR_TYPE_SYSTEM + "unclassified", "this a undefined system error."),

    // detailed system error
    SERIALIZE_ERROR(ResponseCode.ERROR_TYPE_SYSTEM + "serialize", ""),
    NOT_IMPL_ERROR(ResponseCode.ERROR_TYPE_SYSTEM + "not_impl", ""),
    UN_CODED_ERROR(ResponseCode.ERROR_TYPE_SYSTEM + "uncoded", ""),


    // middle ware errors
    RABBIT_MQ_ERROR(ResponseCode.ERROR_TYPE_SYSTEM_MW + "mq", ""),
    JPA_ERROR(ResponseCode.ERROR_TYPE_SYSTEM_MW + "jpa", ""),
    REDIS_ERROR(ResponseCode.ERROR_TYPE_SYSTEM_MW + "redis", ""),


    //
    ;


    public static final String ERROR_TYPE_SYSTEM_MW = ResponseCode.ERROR_TYPE_SYSTEM + "mw_";
    public static final String ERROR_TYPE_SYSTEM = Def.ERR_MARK + "sys_";

    private String code;
    private String message;

    public static void main(String[] args) {
        for (ResponseCode value : ResponseCode.values()) {
            System.out.println(value.getCode());
        }
    }

}
