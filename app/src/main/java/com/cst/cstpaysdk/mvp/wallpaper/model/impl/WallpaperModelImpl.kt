package com.cst.cstpaysdk.mvp.wallpaper.model.impl

import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.cst.cstpaysdk.bean.ReqShopInfoBean
import com.cst.cstpaysdk.bean.ResWallpaperBean
import com.cst.cstpaysdk.mvp.wallpaper.model.IWallpaperModel
import com.cst.cstpaysdk.net.OkHttp3Utils
import com.cst.cstpaysdk.util.ConstantUtils
import com.cst.cstpaysdk.util.LogUtil
import io.reactivex.Observable
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class WallpaperModelImpl : IWallpaperModel {

    override fun getWallpaper(context: Context): Observable<ResWallpaperBean> {
        return Observable.create {
            val reqShopInfoBean = ReqShopInfoBean()
            val param: String = JSON.toJSONString(reqShopInfoBean)
            LogUtil.customLog(context.applicationContext, "获取屏保壁纸推送参数 = $param")

            OkHttp3Utils.get().doPostJson(context, ConstantUtils.getWallpaperUrl(context), param, object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body: ResponseBody? = response.body()
                    val result: String? = body?.string()
                    LogUtil.customLog(context.applicationContext, "获取屏保壁纸响应参数，result = $result")
                    if (result != null && result.startsWith("{") && result.endsWith("}")) {
                        val resWallpaperBean: ResWallpaperBean = JSON.parseObject(result, ResWallpaperBean::class.java)
                        if (resWallpaperBean.code == 200) {
                            if(resWallpaperBean.data != null) {
                                it.onNext(resWallpaperBean)
                            } else {
                                it.onError(Throwable(resWallpaperBean.msg))
                            }
                        } else {
                            it.onError(Throwable(resWallpaperBean.msg ?: "获取屏保壁纸失败"))
                        }
                    } else {
                        it.onError(Throwable("获取屏保壁纸失败"))
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    LogUtil.customLog(context.applicationContext, "获取屏保壁纸失败，e = $e")
                    it.onError(e)
                }
            })
        }
    }
}