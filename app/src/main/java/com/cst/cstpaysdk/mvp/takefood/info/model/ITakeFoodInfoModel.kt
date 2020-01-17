package com.cst.cstpaysdk.mvp.takefood.info.model

import android.content.Context
import com.cst.cstpaysdk.bean.ResTakeFoodInfoBean
import io.reactivex.Observable

interface ITakeFoodInfoModel {

    /**
     * 根据用户编号获取取餐菜品（所点菜品）信息
     *
     * @param context 上下文
     * @param context 用户编号
     */
    fun getTakeFoodInfo(context: Context, userCode: String?): Observable<ResTakeFoodInfoBean>
}