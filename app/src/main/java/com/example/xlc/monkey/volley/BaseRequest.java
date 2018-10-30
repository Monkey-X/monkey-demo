package com.example.xlc.monkey.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author:xlc
 * @date:2018/9/27
 * @descirbe: 基础请求文件
 */
public class BaseRequest {

    private BaseVolleyFragment bvf;
    private RequestConfig config;//网络请求的配置参数
    private BaseVolleyActivity act;

    public BaseRequest() {
    }

    public BaseRequest(BaseVolleyFragment bvf, RequestConfig config) {
        this.bvf = bvf;
        this.config = config;
    }

    public BaseRequest(BaseVolleyActivity act, RequestConfig config) {
        this.act = act;
        this.config = config;
    }

    public void start(VolleyCallback callback) {
        if (config == null) {
            return;
        }
        switch (config.getMethod()) {
            case RequestConfig.GET:
                baseGet(config.getWedAddress(), config.getData(), callback);
                break;
            case RequestConfig.POST:
                break;
            case RequestConfig.PUT:
                break;
            case RequestConfig.UPLOAD:
                break;
        }
    }

    private void baseGet(String url, HashMap<String, String> params, final VolleyCallback callback) {
        if (url == null) {
            return;
        }
        if (params != null && params.size() > 0) {
            url +="?";

            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                Object key = next.getKey();
                Object value = next.getValue();
                url += (key+"="+value+"&");
            }
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response(callback,response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError !=null) {
                    handleError(callback,volleyError);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };

    }

    private void handleError(VolleyCallback callback, VolleyError error) {
        BaseModel t = new BaseModel();
        t.setRequest_code(config.getRequestCode());
        if (error.networkResponse !=null && error.networkResponse.data.length !=0) {
            String body = new String(error.networkResponse.data);

        }


    }

    /**
     * 请求成功回调，将返回的数据进行封装
     * @param callback
     * @param response
     */
    private void response(VolleyCallback callback, String response) {
        BaseModel t = new BaseModel();
        t.setRequest_code(config.getRequestCode());
        t.setResponse(response);
        callback.onTaskSuccess(t);
        callback.onTaskFinished(t);
    }
}
