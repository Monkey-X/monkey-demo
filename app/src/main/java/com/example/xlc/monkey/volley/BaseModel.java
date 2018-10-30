package com.example.xlc.monkey.volley;

import java.util.ArrayList;

/**
 * @author:xlc
 * @date:2018/9/27
 * @descirbe:  网络请求结果类
 */
public class BaseModel<T> {

    /**
     * http状态码，代表http请求状态码状态信息
     */
    private int httpstate;

    /**
     * 接口请求码，由本地决定，对于不用的接口建议传入不同的请求码，用于在存在多个网络请求并发的时候的回调判断
     */
    private String request_code;

    /**
     * http响应具体信息，没有进行对应解析，直接返回
     */
    private String response;

    /**
     * 任务成功 / 失败状态标志量，t代表任务执行成功，f代表任务执行失败
     */
    private String success;

    /**
     * 服务器出错信息，用于显示服务器那边出错以后的错误信息
     */
    private String error;

    /**
     * 接口错误信息，用于前端提示
     */
    private String error_msg;

    /**
     * 接口错误信息，用于前端提示
     */
    private String error_description;

    /**
     * 接口返回的数据模型，实际需要的数据值
     */
    private T result;

    /**
     * 任务成功或者失败的状态标量值，1代表任务执行成功，其他代表失败
     */
    private int code;

    /**
     * 接口错误信息
     */
    private String message;

    /**
     * 接口返回的数据类型，在接口返回值是对象的时候使用
     */
    private T object;

    /**
     * 接口返回的数据类型，在接口返回值是list的时候使用
     */
    private ArrayList<T> list;


    public BaseModel(){
        setTaskFail();
    }

    public int getHttpstate() {
        return httpstate;
    }

    public void setHttpstate(int httpstate) {
        this.httpstate = httpstate;
    }

    public String getRequest_code() {
        return request_code;
    }

    public void setRequest_code(String request_code) {
        this.request_code = request_code;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }

    public boolean isSuccess(){
        if (getCode() == 1) {
            return true;
        }
        return false;
    }

    /**
     * 将这个任务设置为成功
     */
    public void setTaskSuccess(){
        setCode(1);
    }

    /**
     * 将这个任务设置为失败
     */
    public void setTaskFail() {
        setCode(0);
    }
}
