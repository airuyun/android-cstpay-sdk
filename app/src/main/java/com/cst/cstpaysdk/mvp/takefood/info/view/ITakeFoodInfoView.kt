package com.cst.cstpaysdk.mvp.takefood.info.view

import com.cst.cstpaysdk.base.IBaseView
import com.cst.cstpaysdk.bean.ResTakeFoodInfoBean

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do TakeFoodActivity网络请求回调更新UI接口
 * 影响范围：
 * 备注：
 * @cst_end
 */
interface ITakeFoodInfoView : IBaseView {

    fun getTakeFoodInfoSuccess(takeFoodInfo: ResTakeFoodInfoBean)

    fun getTakeFoodInfoFailure(error: Throwable)

}