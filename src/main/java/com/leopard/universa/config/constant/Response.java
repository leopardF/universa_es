package com.leopard.universa.config.constant;

import com.leopard.universa.enums.ResultEnum;
import com.leopard.universa.utils.BeanUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

import static com.leopard.universa.enums.ResultEnum.SUCCESS;

/**
 * 封装返回消息数据格式
 *
 * @author leopard
 */
@Data
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 4308389749594040329L;
    private String code;
    private String message;
    private String requestId = UUID.randomUUID().toString();
    private T data;

    public Response() {
        System.out.println("request id: " + requestId);
    }

    private Response(String code, String message) {
        this.code = code;
        this.message = message;
        System.out.println("request id: " + requestId);
    }

    private Response(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        System.out.println("request id: " + requestId);
    }


    public static Response success() {
        return success(new NullObject());
    }

    public static Response success(Object data) {
        return new Response(ResultEnum.SUCCESS.code(), ResultEnum.SUCCESS.msg(), data);
    }

    public static Response error(String code, String desc) {
        return new Response(code, desc);
    }


    public static Response error(ResultEnum errorCode) {
        return new Response(errorCode.code(), errorCode.msg());
    }

    public Response setRequestId(String requestId) {
        this.requestId = requestId;
        System.out.println("request id: " + requestId);
        return this;
    }


    public Response setDataValue(String field, Object value) {
        try {
            if (data != null) {
                setData(BeanUtil.setProperty(data, field, value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public boolean isSuccess() {
        return SUCCESS.code().equals(code);
    }

}
