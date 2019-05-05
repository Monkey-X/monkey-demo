package com.example.xlc.monkey.keeplive.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity

/**
 *@author:xlc
 *@date:2019/5/5
 *@descirbe:监听锁屏广播，开启1个像素的activity，解锁后将这个activity结束
 */

class  OnePixelActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置一个像素的activity
        val window = window
        window.setGravity(Gravity.LEFT or Gravity.TOP)
        val params = window.attributes
        params.x = 0
        params.y = 0
        params.height = 1
        params.width = 1
        window.attributes = params
        //在一像素activity里注册广播接受者
    }

}