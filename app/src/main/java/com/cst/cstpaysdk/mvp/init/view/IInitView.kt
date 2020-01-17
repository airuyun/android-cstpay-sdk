package com.cst.cstpaysdk.mvp.init.view

import com.cst.cstpaysdk.base.IBaseView
import com.cst.cstpaysdk.bean.ResInitBean

/**
 * @author tjs
 * @date 2019/10/17 19:14
 * @cst_do 康索特三代平台支付网络请求回调更新UI接口
 * 影响范围：
 * 备注：
 * @cst_end
 */
interface IInitView : IBaseView {

    fun initSuccess(resInitBean: ResInitBean?)

    fun initFailure(error: Throwable)
}