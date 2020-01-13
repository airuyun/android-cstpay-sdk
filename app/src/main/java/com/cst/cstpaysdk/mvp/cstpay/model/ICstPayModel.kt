package com.cst.cstpaysdk.mvp.cstpay.model

import android.content.Context
import com.cst.cstpaysdk.bean.PayInfoBean
import com.cst.cstpaysdk.bean.ResCstPayBean
import io.reactivex.Observable

interface ICstPayModel {

    /**
     * 调用康索特三代平台在线支付接口
     *
     * @param context 上下文
     */
    fun cstOnlinePay(context: Context, payInfoBean: PayInfoBean): Observable<ResCstPayBean>

    /**
     * 离线支付
     *
     * @param context 上下文
     */
    fun cstOfflinePay(context: Context, payInfoBean: PayInfoBean): Observable<ResCstPayBean>
}