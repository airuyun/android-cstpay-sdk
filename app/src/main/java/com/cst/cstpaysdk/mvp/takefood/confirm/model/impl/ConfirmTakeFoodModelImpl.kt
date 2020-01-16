package com.cst.cstpaysdk.mvp.takefood.confirm.model.impl

import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.cst.cstpaysdk.bean.ReqConfirmTakeFoodBean
import com.cst.cstpaysdk.bean.ResTakeFoodInfoBean
import com.cst.cstpaysdk.mvp.takefood.confirm.model.IConfirmTakeFoodModel
import com.cst.cstpaysdk.net.OkHttp3Utils
import com.cst.cstpaysdk.util.ConstantUtils
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
 * @cst_do 功能描述：确认取餐的M层，网络请求返回数据的处理
 * 影响范围：确认取餐
 * 备注：通过菜品订单编号的集合确认取餐
 * @cst_end
 */
class ConfirmTakeFoodModelImpl : IConfirmTakeFoodModel {

    override fun confirmTakeFood(context: Context, takeFoodInfo: ResTakeFoodInfoBean?): Observable<Boolean> {
        return Observable.create {
            //获取菜品订单编号
            val fbOrderIdList: MutableList<String> = mutableListOf()
            takeFoodInfo?.let {
                for(foodId in takeFoodInfo.menuInfoList!!) {
                    foodId.fbOrderId?.let { it1 -> fbOrderIdList.add(it1) }
                }
            }

            val reqConfirmTakeFoodBean = ReqConfirmTakeFoodBean()
            reqConfirmTakeFoodBean.data.fbOrderIds = fbOrderIdList
            val param: String = JSON.toJSONString(reqConfirmTakeFoodBean)
            LogUtil.customLog(context, "确认取餐推送参数 = $param")

            OkHttp3Utils.get().doPostJson(context, ConstantUtils.getConfirmTakeFoodUrl(context), param, object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body: ResponseBody? = response.body()
                    val resultJson: String? = body?.string()
                    LogUtil.customLog(context, "确认取餐成功 = $resultJson")
                    val jsonObject: JSONObject = JSON.parseObject(resultJson)

                    if(jsonObject.getString("code") == "200") {
                        it.onNext(true)
                    } else {
                        it.onError(Throwable(jsonObject.getString("msg") ?: "确认取餐失败"))
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    LogUtil.customLog(context, "确认取餐失败 = $e")
                    it.onError(e)
                }
            })
        }
    }
}