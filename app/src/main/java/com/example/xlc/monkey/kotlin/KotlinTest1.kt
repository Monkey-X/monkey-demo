package com.example.xlc.monkey.kotlin

/**
 *@author:xlc
 *@date:2019/7/2
 *@descirbe:创建装饰者模式
 */
interface MacBook {
    fun getCost(): Int
    fun getDesc(): String
    fun getProdDate(): String
}

class MacBookPro : MacBook {

    override fun getCost(): Int = 10000

    override fun getDesc(): String {
        return "MacBook Pro"
    }

    override fun getProdDate(): String {
       return  "Late 2011"
    }
}

//装饰类
class ProcessorUpgradeMacBookPro(val macbook :MacBook) : MacBook by macbook{
    override fun getCost(): Int {
       return  macbook.getCost()+219
    }

    override fun getDesc(): String {
        return macbook.getDesc()+", +1G Memory"
    }


}

fun main(arg: Array<String>){
    val macBookPro = MacBookPro()
    val processorUpgradeMacBookPro = ProcessorUpgradeMacBookPro(macBookPro)
    println(processorUpgradeMacBookPro.getCost())
    println(processorUpgradeMacBookPro.getDesc())
    println(processorUpgradeMacBookPro.getProdDate())
}