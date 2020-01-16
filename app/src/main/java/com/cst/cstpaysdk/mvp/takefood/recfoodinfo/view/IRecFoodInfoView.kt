package com.cst.cstpaysdk.mvp.takefood.recfoodinfo.view

import com.cst.cstpaysdk.base.IBaseView
import com.cst.cstpaysdk.bean.ResRecFoodInfoBean

/**
 * @author tjs
 * @date 2019/07/7 09:32
 * @cst_do MainActivity网络请求回调更新UI接口
 * 影响范围：
 * 备注：
 * @cst_end
 */
interface IRecFoodInfoView : IBaseView {

    fun getRecFoodInfoSuccess(recFoodInfo: ResRecFoodInfoBean)

    fun getRecFoodInfoFailure(error: Throwable)
}