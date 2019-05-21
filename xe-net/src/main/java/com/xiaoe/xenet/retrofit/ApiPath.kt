package com.xiaoe.xenet.retrofit

import androidx.annotation.StringDef

object ApiPath {
    const val Internal = "Internal"
    const val Test = "Test"
    const val Beta = "Beta"
    const val Formal = "Formal"
    val desMap = mapOf(
            Pair(Internal, "内网环境"),
            Pair(Test, "测试环境"),
            Pair(Beta, "Beta环境"),
            Pair(Formal, "正式环境"))

    @StringDef(Internal, Test, Beta, Formal)
    annotation class EnvironmentType

    var currentEnvir /*by pref(Test)*/=""//todo


    fun setEnvironment(@EnvironmentType environment: String) {
        currentEnvir = environment
    }

    fun getApiPath(envir: String = currentEnvir): String =
            "http://v3.wufazhuce.com:8000"

}