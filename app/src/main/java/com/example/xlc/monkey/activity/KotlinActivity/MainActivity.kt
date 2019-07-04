package com.example.xlc.monkey.activity.KotlinActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.xlc.monkey.R
import kotlinx.android.synthetic.main.activity_kotlin_main.*

/**
 *@author:xlc
 *@date:2019/7/4
 *@descirbe:显示自定义的圆角和阴影属性
 */
class  MainActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_main)
        val url = "https://n.sinaimg.cn/tech/transform/560/w280h280/20190429/SpUs-hwfpcxm9530435.gif"
        Glide.with(this@MainActivity).load(url).into(iv_head)
    }
}
