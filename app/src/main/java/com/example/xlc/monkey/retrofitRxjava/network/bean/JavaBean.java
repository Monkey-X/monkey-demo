package com.example.xlc.monkey.retrofitRxjava.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author:xlc
 * @date:2018/9/19
 * @descirbe:
 */
public class JavaBean implements Parcelable{


    private int carId;

    private int pNumber;

    private String autoPay;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getpNumber() {
        return pNumber;
    }

    public void setpNumber(int pNumber) {
        this.pNumber = pNumber;
    }

    public String getAutoPay() {
        return autoPay;
    }

    public void setAutoPay(String autoPay) {
        this.autoPay = autoPay;
    }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(carId);
        dest.writeInt(pNumber);
        dest.writeString(autoPay);
    }

    /**
     * 这里读的顺序必须与writeToParcel()中书写的顺序一样
     * 否则数据会有差错
     * @param in
     */
    protected JavaBean(Parcel in) {
        carId = in.readInt();
        pNumber = in.readInt();
        autoPay = in.readString();
    }

    public static final Creator<JavaBean> CREATOR = new Creator<JavaBean>() {
        @Override
        public JavaBean createFromParcel(Parcel in) {
            return new JavaBean(in);
        }

        @Override
        public JavaBean[] newArray(int size) {
            return new JavaBean[size];
        }
    };
}
