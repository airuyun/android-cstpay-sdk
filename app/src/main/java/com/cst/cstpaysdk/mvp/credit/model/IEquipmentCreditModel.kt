package com.cst.cstpaysdk.mvp.credit.model

import android.content.Context
import com.cst.cstpaysdk.bean.ResCstPayBean
import io.reactivex.Observable

interface IEquipmentCreditModel {

    /**
     * 处理后台推送的设备信用信息
     */
    fun reqEquipmentCreditInfo(context: Context, data: String?): Observable<ResCstPayBean>

    /**
     * 处理后台推送的设备信用信息
     */
    fun resEquipmentCreditInfo(context: Context, data: String?): Observable<ResCstPayBean>
}