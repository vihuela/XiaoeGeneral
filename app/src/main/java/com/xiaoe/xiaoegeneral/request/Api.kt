
package com.xiaoe.xiaoegeneral.request

import com.xiaoe.common.retrofit.ApiUtils
import com.xiaoe.xiaoegeneral.request.model.ImageRequest
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    companion object {
        val listConfig = mapOf(
                Pair("pageSize", 10),
                Pair("startOffset", 1))
                .withDefault { 0 }

        val IMPL: Api = ApiUtils.get(Api::class.java)
        val pageSize by listConfig
        val startOffset by listConfig
    }

    @GET("http://gank.io/api/data/福利/{size}/{page}")
    fun getImageList(@Path("size") size: Int, @Path("page") page: Int): Observable<ImageRequest>

}