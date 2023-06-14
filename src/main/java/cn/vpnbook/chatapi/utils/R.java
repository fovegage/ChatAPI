package cn.vpnbook.chatapi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class R {
    public static final Integer SUCCESS = 0;
    public static final Integer FAIL = 1;
    private Integer code;
    private Object data;
    private String msg;

    public static R success(Object data) {
        return new R(SUCCESS, data, null);
    }

    public static R fail(String msg) {
        return new R(FAIL, null, msg);
    }

    public static R fail(Object data, String msg) {
        return new R(FAIL, data, msg);
    }

}
