package com.xiaoe.common.retrofit.request

open class BaseRequest<out T> {
//    val code: Int = -1
    val res :Int = -1
    val msg: String = ""
    val data: T? = null


}