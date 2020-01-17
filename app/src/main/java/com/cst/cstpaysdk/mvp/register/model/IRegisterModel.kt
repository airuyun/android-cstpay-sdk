package com.cst.cstpaysdk.mvp.register.model

import android.content.Context
import com.cst.cstpaysdk.bean.ReqInitBean
import com.cst.cstpaysdk.bean.ResRegisterBean
import io.reactivex.Observable

interface IRegisterModel {

    /**
     * 请求入网（将设备注册到平台）
     *
     * @param context 上下文
     */
    fun register(context: Context, reqInitBean: ReqInitBean?): Observable<ResRegisterBean>
}