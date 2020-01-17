package com.cst.cstpaysdk.mvp.takefood.recfoodinfo.model.impl

import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.cst.cstpaysdk.bean.ReqRecFoodInfoBean
import com.cst.cstpaysdk.bean.ResRecFoodInfoBean
import com.cst.cstpaysdk.mvp.takefood.recfoodinfo.model.IRecFoodInfoModel
import com.cst.cstpaysdk.net.OkHttp3Utils
import com.cst.cstpaysdk.util.ConstantUtils
import com.cst.cstpaysdk.util.LogUtil
import io.reactivex.Observable
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class RecFoodInfoModelImpl : IRecFoodInfoModel {

    override fun getRecFoodInfo(context: Context): Observable<ResRecFoodInfoBean> {

        return Observable.create {
            val reqRecFoodInfoBean = ReqRecFoodInfoBean()
            reqRecFoodInfoBean.data.shopId = ConstantUtils.shopId
            val param: String = JSON.toJSONString(reqRecFoodInfoBean)
            LogUtil.customLog(context, "获取推荐菜品图片推送参数 = $param")

            OkHttp3Utils.get().doPostJson(context, ConstantUtils.getRecFoodInfoUrl(context), param, object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body: ResponseBody? = response.body()
                    val resultJson: String? = body?.string()
                    LogUtil.customLog(context, "获取推荐菜品图片成功 = $resultJson")
                    val jsonObject: JSONObject = JSON.parseObject(resultJson)

                    //数据处理
                    if(jsonObject.getString("code") == "200") {
                        val resRecFoodInfoBean: ResRecFoodInfoBean = JSON.parseObject(jsonObject.getString("data"), ResRecFoodInfoBean::class.java)
                        it.onNext(resRecFoodInfoBean)
                    } else {
                        it.onError(Throwable(jsonObject.getString("msg")))
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    LogUtil.customLog(context, "获取推荐菜品图片失败 = $e")
                    it.onError(e)
                }
            })
        }
    }
}