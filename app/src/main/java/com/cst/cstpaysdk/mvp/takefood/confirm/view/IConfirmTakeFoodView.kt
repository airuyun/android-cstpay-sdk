package com.cst.cstpaysdk.mvp.takefood.confirm.view

import com.cst.cstpaysdk.base.IBaseView

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do TakeFoodActivity网络请求回调更新UI接口
 * 影响范围：
 * 备注：
 * @cst_end
 */
interface IConfirmTakeFoodView : IBaseView {

    fun confirmTakeFoodSuccess(confirmSuccess: Boolean)

    fun confirmTakeFoodFailure(error: Throwable)

}