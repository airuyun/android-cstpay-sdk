package com.cst.cstpaysdk.mvp.cstpay.view

import com.cst.cstpaysdk.base.IBaseView
import com.cst.cstpaysdk.bean.ResCstPayBean

/**
 * @author tjs
 * @date 2019/10/17 19:14
 * @cst_do 康索特三代平台支付网络请求回调更新UI接口
 * 影响范围：
 * 备注：
 * @cst_end
 */
interface ICstPayView : IBaseView {

    fun cstPaySuccess(resCstPayBean: ResCstPayBean)

    fun cstPayFailure(error: Throwable)
}