package com.example.xlc.monkey.mvp.bean;

/**
 * @author:xlc
 * @date:2018/9/14
 * @descirbe: 请求返回的基类,
 */
public class BaseObjectBean<T> {
    /**
     * status:1
     * msg:获取成功
     * result:{}对象
     */

    private int errorCode;
    private String errorString;
    private T result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
