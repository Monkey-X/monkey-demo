package com.example.xlc.monkey.mvp.bean;

import java.util.List;

/**
 * @author:xlc
 * @date:2018/9/14
 * @descirbe: 获取的对象数组
 */
public class BaseArrayBean<T> {

    private int errorCode;
    private String errorMsg;
    private List<T> result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
