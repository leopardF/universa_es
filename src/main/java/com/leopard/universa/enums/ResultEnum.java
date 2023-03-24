package com.leopard.universa.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 状态码
 *
 * @author leopard
 */
public enum ResultEnum {

    /****/
    SUCCESS("0", "success"),
    FAILURE("-1", "系统异常"),
    AUTH("-2", "签名过期或校验不通过");

    private String code;
    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取枚举值列表
     *
     * @return
     */
    public static List<Map<String, Object>> list() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        ResultEnum[] values = ResultEnum.values();
        for (ResultEnum systemCodeAndMsg : values) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", systemCodeAndMsg.code());
            map.put("msg", systemCodeAndMsg.msg());
            list.add(map);
        }
        return list;
    }

    /**
     * 通过code获取msg
     *
     * @param code
     * @return
     */
    public static String findMsgByCode(String code) {
        ResultEnum[] values = ResultEnum.values();
        for (ResultEnum tempEnum : values) {
            if (code.equals(tempEnum.code())) {
                return tempEnum.msg();
            }
        }
        return "";
    }


    public String code() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String msg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
