package com.cst.cstpaysdk.mvp.takefood.recfoodinfo.model

import android.content.Context
import com.cst.cstpaysdk.bean.ResRecFoodInfoBean
import io.reactivex.Observable

interface IRecFoodInfoModel {

    /**
     * 获取商铺菜品图片
     *
     * @param context 上下文
     */
    fun getRecFoodInfo(context: Context): Observable<ResRecFoodInfoBean>
}