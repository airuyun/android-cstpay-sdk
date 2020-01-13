package com.cst.cstpaysdk.mvp.shopinfo.model

import android.content.Context
import com.cst.cstpaysdk.bean.ResShopInfoBean
import io.reactivex.Observable

interface IShopInfoModel {

    /**
     * 获取店铺信息
     *
     * @param context 上下文
     */
    fun getShopInfo(context: Context): Observable<ResShopInfoBean>
}