package com.example.xlc.monkey.volley;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author:xlc
 * @date:2018/9/27
 * @descirbe:  request的配置
 */
public class RequestConfig {

    public static final String GET = "get";//get请求方式
    public static final String POST = "post";//post请求方式
    public static final String PUT ="put";//put请求方式
    public static final String UPLOAD = "upload";//post请求方式

    private String requestCode = null; //请求码
    private String method = null;//请求方式
    private String wedAddress = null;//网络请求地址
    private String authName = null;//basic 认证用户名
    private String authPswd = null;//basic 认证用户密码
    private HashMap<String,String> data = null; //请求数据（httpbody）
    private String requestdata; //采用raw方式进行数据请求时的请求数据，即没有键，没有值的情况
    private HashMap<String,String> header = null;//请求头
    private HashMap<String,String> files = null;//发送文件内容（key可以存放用户名，value存放文件路径，路径是一个完整的路径）
    private JSONObject jsonObject = null;//json参数

//    body
    private String bodyStr;
    private Class<?> cls; //json解析数据末班
    private Class<?> element;  //当cls是arraylist的时候的列表元素
    private boolean needParse = true;//是否需要对数据进行解析，如果不需要进行数据解析，那么久返回原始数据对象，包含http状态码


    public RequestConfig(){}

    public RequestConfig(String requestCode){
        this.requestCode = requestCode;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getWedAddress() {
        return wedAddress;
    }

    public void setWedAddress(String wedAddress) {
        this.wedAddress = wedAddress;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthPswd() {
        return authPswd;
    }

    public void setAuthPswd(String authPswd) {
        this.authPswd = authPswd;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public String getRequestdata() {
        return requestdata;
    }

    public void setRequestdata(String requestdata) {
        this.requestdata = requestdata;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    public HashMap<String, String> getFiles() {
        return files;
    }

    public void setFiles(HashMap<String, String> files) {
        this.files = files;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getBodyStr() {
        return bodyStr;
    }

    public void setBodyStr(String bodyStr) {
        this.bodyStr = bodyStr;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public Class<?> getElement() {
        return element;
    }

    public void setElement(Class<?> element) {
        this.element = element;
    }

    public boolean isNeedParse() {
        return needParse;
    }

    public void setNeedParse(boolean needParse) {
        this.needParse = needParse;
    }
}
