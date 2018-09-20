package com.example.xlc.monkey.avoidonresult;

import android.content.Intent;

/**
 * @author:xlc
 * @date:2018/9/20
 * @descirbe:处理startActivityForResult的信息类
 */
public class ActivityResultInfo {

    private int resultCode;
    private Intent data;

    public ActivityResultInfo(int resultCode,Intent data){
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Intent getData() {
        return data;
    }

    public void setData(Intent data) {
        this.data = data;
    }
}
