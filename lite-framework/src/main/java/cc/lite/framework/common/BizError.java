package cc.lite.framework.common;


import cc.lite.framework.type.IResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BizError extends RuntimeException {
    private IResponseCode responseCode;


    public String toString() {
        return responseCode == null ? "" : "[" + responseCode.getCode() + "]" + "[" + responseCode.getMessage() + "]";
    }
}
