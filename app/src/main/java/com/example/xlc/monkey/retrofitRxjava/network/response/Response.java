package com.example.xlc.monkey.retrofitRxjava.network.response;

/**
 * @author:xlc
 * @date:2018/9/19
 * @descirbe: 和后台约定返回的json数据格式
 */
public class Response<T> {

    private int code;
    private T data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
