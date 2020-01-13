package com.cst.cstpaysdk.net

import android.content.Context
import android.os.Environment
import android.util.Log
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * @author TJS
 * @date 2019/07/20 14:40
 * @cst_do 功能描述：OkHttp3网络请求工具栏
 * 影响范围：OkHttp3网络请求数据
 * 备注：
 * @cst_end
 */
class OkHttp3Utils1 private constructor() {

    private var okHttpClient: OkHttpClient? = null

    @Synchronized
    fun getOkHttpClient(context: Context): OkHttpClient? {
        if (okHttpClient == null) {

            //缓存目录
            val cacheFile = File(Environment.getExternalStorageDirectory(), "cache")
            val cacheSize = 10 * 1024 * 1024

            //OkHttp3拦截器
            val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.i("xxx", message.toString()) })

            //OkHttp3的拦截器日志分类 4种
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            okHttpClient = OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                //添加OkHttp3的拦截器
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(CacheInterceptor(context))
                .writeTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS)
                .cache(Cache(cacheFile.absoluteFile, cacheSize.toLong()))
                .build()
        }
        return okHttpClient
    }

    /**
     * Post请求发送JSON数据
     * @param url 请求Url
     * @param jsonParams 请求的JSON字符串
     * @param callback 请求回调
     */
    fun doPostJson(context: Context, url: String, jsonParams: String, callback: Callback) {
        val requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams)
        val request = Request.Builder().url(url).post(requestBody).build()
        val call = getOkHttpClient(context)?.newCall(request)
        call?.enqueue(callback)
    }

    /**
     * 为OkHttp添加缓存，这里是考虑到服务器不支持缓存时，从而让OkHttp支持缓存
     */
    private class CacheInterceptor(val context: Context) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            // 有网络时 设置缓存超时时间1个小时
            val maxAge = 60 * 60

            // 无网络时，设置超时为1天
            val maxStale = 60 * 60 * 24

            var request = chain.request()
            if (NetworkUtils.isConnected(context)) {
                //有网络时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build()
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
                /* Looper.prepare();
                Toast.makeText(MyApp.getInstance(), "走拦截器缓存", Toast.LENGTH_SHORT).show();
                Looper.loop();*/
            }
            var response = chain.proceed(request)
            if (NetworkUtils.isConnected(context)) {
                response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()
            } else {
                response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
            }
            return response
        }
    }

    companion object {
        private var instance: OkHttp3Utils1? = null
            get() {
                if (field == null) {
                    field = OkHttp3Utils1()
                }
                return field
            }
        @Synchronized
        fun get(): OkHttp3Utils1{
            return instance!!
        }
    }
}