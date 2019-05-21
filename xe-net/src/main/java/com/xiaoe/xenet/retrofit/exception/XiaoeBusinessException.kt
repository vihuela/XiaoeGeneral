package com.xiaoe.xenet.retrofit.exception

class XiaoeBusinessException(message: String, val code: Int = -1) : Throwable(message)
