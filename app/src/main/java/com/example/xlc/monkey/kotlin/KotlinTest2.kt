package com.example.xlc.monkey.kotlin

/**
 *@author:xlc
 *@date:2019/7/2
 *@descirbe:通过拓展代替装饰者
 */
class Printer{
    fun drawLine(){
        println("------------")
    }

    fun drawDottedLine(){
        println("~~~~~~~~~~~~")
    }

    fun drawStars(){
        println("************")
    }
}

fun Printer.startDraw(decorated:Printer.() -> Unit){
    println("+++ Start drawing +++")
    this.decorated()
    println("+++ end drawing +++")
}

fun main(args:Array<String>){
    Printer().run {
        startDraw {
            drawLine()
        }

        startDraw {
            drawDottedLine()
        }

        startDraw {
            drawStars()
        }
    }
}