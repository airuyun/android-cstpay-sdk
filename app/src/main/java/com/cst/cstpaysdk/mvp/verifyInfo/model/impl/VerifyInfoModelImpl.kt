package com.cst.cstpaysdk.mvp.verifyInfo.model.impl

import android.content.Context
import com.alibaba.fastjson.JSON
import com.cst.cstpaysdk.bean.ReqVerifyInfoBean
import com.cst.cstpaysdk.bean.ResVerifyInfoBean
import com.cst.cstpaysdk.mvp.verifyInfo.model.IVerifyInfoModel
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

class VerifyInfoModelImpl : IVerifyInfoModel {

    override fun getVerifyInfo(context: Context, secretKeyType: String): Observable<ResVerifyInfoBean> {
        return Observable.create {
            val reqVerifyInfoBean = ReqVerifyInfoBean()
            reqVerifyInfoBean.equipmentId = ConstantUtils.getEquipmentId()
            reqVerifyInfoBean.mac = LocalUtils.getMac().replace(":", "")
            reqVerifyInfoBean.type = secretKeyType
            val param: String = JSON.toJSONString(reqVerifyInfoBean)
            LogUtil.customLog(context.applicationContext, "获取验签信息推送参数 = $param")

            OkHttp3Utils.get().doPostJson(context, ConstantUtils.getSecretKeyUrl(), param, object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body: ResponseBody? = response.body()
                    val result: String? = body?.string()
                    LogUtil.customLog(context.applicationContext, "获取验签信息响应参数，result = $result")
                    if (result != null && result.startsWith("{") && result.endsWith("}")) {
                        val resVerifyInfoBean: ResVerifyInfoBean = JSON.parseObject(result, ResVerifyInfoBean::class.java)
                        if (resVerifyInfoBean.code == 200) {
                            if(resVerifyInfoBean.data != null) {
                                it.onNext(resVerifyInfoBean)
                            } else {
                                it.onError(Throwable(resVerifyInfoBean.msg))
                            }
                        } else {
                            it.onError(Throwable(resVerifyInfoBean.msg ?: "获取验签信息失败"))
                        }
                    } else {
                        it.onError(Throwable("获取验签信息失败"))
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    LogUtil.customLog(context.applicationContext, "获取验签信息失败，e = $e")
                    it.onError(e)
                }
            })
        }
    }
}