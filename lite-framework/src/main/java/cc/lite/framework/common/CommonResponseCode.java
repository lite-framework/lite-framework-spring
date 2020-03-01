package cc.lite.framework.common;


import cc.lite.framework.Def;
import cc.lite.framework.type.IResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommonResponseCode implements IResponseCode {


    // biz
    SUCCESS("success", ""),
    ACCEPTED("accepted", ""),
    DUP_REQUEST("dup_request", ""),
    PARAM_ERROR(Def.ERR_MARK + "param", ""),
    REQUEST_RATE_LIMITED(Def.ERR_MARK + "request_limited", ""),


    // system error
    SYSTEM_ERROR(CommonResponseCode.ERROR_TYPE_SYSTEM + "unclassified", "this a undefined system error."),

    // detailed system error
    SERIALIZE_ERROR(CommonResponseCode.ERROR_TYPE_SYSTEM + "serialize", ""),
    NOT_IMPL_ERROR(CommonResponseCode.ERROR_TYPE_SYSTEM + "not_impl", ""),
    UN_CODED_ERROR(CommonResponseCode.ERROR_TYPE_SYSTEM + "uncoded", ""),


    // middle ware errors
    RABBIT_MQ_ERROR(CommonResponseCode.ERROR_TYPE_SYSTEM_MW + "mq", ""),
    JPA_ERROR(CommonResponseCode.ERROR_TYPE_SYSTEM_MW + "jpa", ""),
    REDIS_ERROR(CommonResponseCode.ERROR_TYPE_SYSTEM_MW + "redis", ""),


    //
    ;


    public static final String ERROR_TYPE_SYSTEM_MW = CommonResponseCode.ERROR_TYPE_SYSTEM + "mw_";
    public static final String ERROR_TYPE_SYSTEM = Def.ERR_MARK + "sys_";

    private String code;
    private String message;

    public static void main(String[] args) {
        for (CommonResponseCode value : CommonResponseCode.values()) {
            System.out.println(value.getCode());
        }
    }

}
