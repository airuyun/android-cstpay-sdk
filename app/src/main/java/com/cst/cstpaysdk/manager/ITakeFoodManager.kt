package com.cst.cstpaysdk.manager

import com.cst.cstpaysdk.bean.ResTakeFoodInfoBean
import com.cst.cstpaysdk.mvp.takefood.confirm.view.IConfirmTakeFoodView
import com.cst.cstpaysdk.mvp.takefood.info.view.ITakeFoodInfoView
import com.cst.cstpaysdk.mvp.takefood.recfoodinfo.view.IRecFoodInfoView

internal interface ITakeFoodManager {

    /**
     * 获取店铺的精品菜（推荐菜品）信息
     */
    fun getRecFoodInfo(iRecFoodInfoView: IRecFoodInfoView?)

    /**
     * 获取用户在该店铺的取餐信息
     */
    fun getTakeFoodInfo(userCode: String?, iTakeFoodInfoView: ITakeFoodInfoView?)

    /**
     * 确认取餐
     */
    fun confirmTakeFood(takeFoodInfo: ResTakeFoodInfoBean?, iConfirmTakeFoodView: IConfirmTakeFoodView?)
}