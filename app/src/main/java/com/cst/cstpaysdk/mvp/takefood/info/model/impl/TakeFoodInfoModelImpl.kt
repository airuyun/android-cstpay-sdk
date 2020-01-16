package com.cst.cstpaysdk.mvp.takefood.info.model.impl

import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.cst.cstpaysdk.bean.ReqTakeFoodInfoBean
import com.cst.cstpaysdk.bean.ResTakeFoodInfoBean
import com.cst.cstpaysdk.mvp.takefood.info.model.ITakeFoodInfoModel
import com.cst.cstpaysdk.net.OkHttp3Utils
import com.cst.cstpaysdk.util.ConstantUtils
import com.cst.cstpaysdk.util.LocalUtils
import com.cst.cstpaysdk.util.LogUtil
import io.reactivex.Observable
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do 功能描述：获取取餐菜品（所点菜品）的M层，将网络请求返回的数据封装成实体类
 * 影响范围：获取取餐菜品（所点菜品）
 * 备注：
 * @cst_end
 */
class TakeFoodInfoModelImpl : ITakeFoodInfoModel {

    override fun getTakeFoodInfo(context: Context, shopId: String?, userCode: String?): Observable<ResTakeFoodInfoBean> {
        return Observable.create {
            val reqTakeFoodInfoBean = ReqTakeFoodInfoBean()
            reqTakeFoodInfoBean.data.shopId = shopId
            reqTakeFoodInfoBean.data.userCode = userCode
            val param: String = JSON.toJSONString(reqTakeFoodInfoBean)
            LogUtil.customLog(context, "获取取餐信息推送参数 = $param")

            OkHttp3Utils.get().doPostJson(context, ConstantUtils.getTakeFoodInfoUrl(context), param, object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body: ResponseBody? = response.body()
                    val resultJson: String? = body?.string()
                    LogUtil.customLog(context, "获取取餐信息成功 = $resultJson")
                    val jsonObject: JSONObject = JSON.parseObject(resultJson)

                    if(jsonObject.getString("code") == "200") {
                        val takeFoodInfo: ResTakeFoodInfoBean = JSON.parseObject(jsonObject.getString("data"), ResTakeFoodInfoBean::class.java)
                        it.onNext(takeFoodInfo)
                    } else {
                        it.onError(Throwable(jsonObject.getString("msg") ?: "获取取餐信息失败"))
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    LogUtil.customLog(context, "获取取餐信息失败 = $e")
                    it.onError(e)
                }
            })
        }
    }
}