package com.example.xlc.monkey.entity;

/**
 * created by xlc at 2018/8/8
 * 时间轴的实体类
 */
public class Trace {

    private String acceptTime;

    private String acceptStation;

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptStation() {
        return acceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        this.acceptStation = acceptStation;
    }

    public Trace() {
    }

    public Trace(String acceptTime,String acceptStation){
        this.acceptTime = acceptTime;
        this.acceptStation = acceptStation;
    }
}
