package com.cst.cstpaysdk.mvp.shopinfo.model.impl

import android.content.Context
import com.alibaba.fastjson.JSON
import com.cst.cstpaysdk.bean.ReqShopInfoBean
import com.cst.cstpaysdk.bean.ResShopInfoBean
import com.cst.cstpaysdk.mvp.shopinfo.model.IShopInfoModel
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

class ShopInfoModelImpl : IShopInfoModel {

    override fun getShopInfo(context: Context): Observable<ResShopInfoBean> {
        return Observable.create {
            val reqShopInfoBean = ReqShopInfoBean()
            reqShopInfoBean.data.equipmentNo = ConstantUtils.getEquipmentNo()
            reqShopInfoBean.data.mac = LocalUtils.getMac().replace(":", "")
            val param: String = JSON.toJSONString(reqShopInfoBean)
            LogUtil.customLog(context.applicationContext, "获取店铺信息请求参数 = $param")

            OkHttp3Utils.get().doPostJson(context, ConstantUtils.getShopInfoUrl(), param, object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body: ResponseBody? = response.body()
                    val result: String? = body?.string()
                    LogUtil.customLog(context.applicationContext, "获取店铺信息响应参数，result = $result")
                    if (result != null && result.startsWith("{") && result.endsWith("}")) {
                        val resShopInfoBean: ResShopInfoBean = JSON.parseObject(result, ResShopInfoBean::class.java)
                        if (resShopInfoBean.code == 200) {
                            if(resShopInfoBean.data != null) {
                                it.onNext(resShopInfoBean)
                            } else {
                                it.onError(Throwable(resShopInfoBean.msg))
                            }
                        } else {
                            it.onError(Throwable(resShopInfoBean.msg ?: "获取店铺信息失败"))
                        }
                    } else {
                        it.onError(Throwable("获取店铺信息失败"))
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    it.onError(e)
                }
            })
        }
    }
}