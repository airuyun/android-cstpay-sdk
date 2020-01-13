package com.cst.cstpaysdk.mvp.verifyInfo.model

import android.content.Context
import com.cst.cstpaysdk.bean.ResVerifyInfoBean
import io.reactivex.Observable

interface IVerifyInfoModel {

    /**
     * 获取验签信息
     *
     * @param context 上下文
     */
    fun getVerifyInfo(context: Context, secretKeyType: String): Observable<ResVerifyInfoBean>
}