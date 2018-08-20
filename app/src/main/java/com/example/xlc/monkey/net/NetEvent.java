package com.example.xlc.monkey.net;

/**
 * 网络变化的接受的接口
 */
public interface NetEvent {

    /**
     * 网络变化后的返回的状态值
     * @param netState
     */
    void onNetChange(int netState);
}
