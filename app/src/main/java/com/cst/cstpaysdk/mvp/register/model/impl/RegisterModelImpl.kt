package com.cst.cstpaysdk.mvp.register.model.impl

import android.content.Context
import com.alibaba.fastjson.JSON
import com.cst.cstpaysdk.bean.ReqInitBean
import com.cst.cstpaysdk.bean.ReqRegisterBean
import com.cst.cstpaysdk.bean.ResRegisterBean
import com.cst.cstpaysdk.mvp.register.model.IRegisterModel
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

class RegisterModelImpl : IRegisterModel {

    /**
     * 入网申请（将设备注册到平台）
     *
     * @param context 上下文
     * @return 被观察者
     */
    override fun register(context: Context, reqInitBean: ReqInitBean?): Observable<ResRegisterBean> {
        return Observable.create {
            val reqRegisterBean = ReqRegisterBean()
            //设备类型，0201-人脸挂式消费机 0202-人脸双屏消费机
            //0203-人脸面板消费机 0204-普通二维码消费机 205-普通消费机
            reqRegisterBean.equipmentType = reqInitBean?.equipmentType
            reqRegisterBean.mac = LocalUtils.getMac().replace(":", "")
            reqRegisterBean.ip = LocalUtils.getLocalInetAddress().hostAddress
            val param: String = JSON.toJSONString(reqRegisterBean)
            LogUtil.customLog(context.applicationContext, "入网申请推送参数 = $param")
            OkHttp3Utils.get().doPostJson(context, ConstantUtils.getRegisterUrl(context), param, object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body: ResponseBody? = response.body()
                    val result: String? = body?.string()
                    LogUtil.customLog(context.applicationContext, "入网申请响应数据，result = $result")
                    if (result != null && result.startsWith("{") && result.endsWith("}")) {
                        val resRegisterBean: ResRegisterBean = JSON.parseObject(result, ResRegisterBean::class.java)
                        if (resRegisterBean.code == 200) {
                            val equipmentId: String? = resRegisterBean.data?.equipmentId
                            val equipmentNo: String? = resRegisterBean.data?.equipmentNo
                            if (equipmentId == null || equipmentId.isEmpty() || equipmentNo == null || equipmentNo.isEmpty()) {
                                it.onError(Throwable("入网失败"))
                            } else {
                                it.onNext(resRegisterBean)
                            }
                        } else {
                            it.onError(Throwable(resRegisterBean.msg ?: "入网失败"))
                        }
                    } else {
                        it.onError(Throwable("入网失败"))
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    LogUtil.customLog(context.applicationContext, "入网失败，e = $e")
                    it.onError(Throwable("入网失败"))
                }
            })
        }
    }
}