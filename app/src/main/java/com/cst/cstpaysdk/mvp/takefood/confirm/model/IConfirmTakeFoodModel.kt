package com.cst.cstpaysdk.mvp.takefood.confirm.model

import android.content.Context
import com.cst.cstpaysdk.bean.ResTakeFoodInfoBean
import io.reactivex.Observable

interface IConfirmTakeFoodModel {

    /**
     * 确认取餐
     *
     * @param context 上下文
     * @param takeFoodInfo 取餐信息（所点菜品）集合
     */
    fun confirmTakeFood(context: Context, takeFoodInfo: ResTakeFoodInfoBean?): Observable<Boolean>
}