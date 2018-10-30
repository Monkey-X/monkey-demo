package com.example.xlc.monkey.volley;

/**
 * @author:xlc
 * @date:2018/9/27
 * @descirbe: 网络请求之后的回调
 */
public interface VolleyCallback {

    /**
     * 成功的回调
     * @param result
     *
     * SuppressWarnings压制警告，即去除警告 rawtypes是说传参时也要传递带泛型的参数
     */
    @SuppressWarnings("rawtypes")
    public abstract void onTaskSuccess(BaseModel result);


    /**
     * 失败回调
     * @param result
     */
    @SuppressWarnings("rawtypes")
    public void onTaskFail(BaseModel result);


    /**
     * 通信完成回调
     * @param result
     */
    public void onTaskFinished(BaseModel result);
}
