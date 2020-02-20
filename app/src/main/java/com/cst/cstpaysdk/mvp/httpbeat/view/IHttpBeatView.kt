package com.cst.cstpaysdk.mvp.httpbeat.view

import com.cst.cstpaysdk.base.IBaseView
import com.cst.cstpaysdk.bean.ResBeatConnectBean

/**
 * @author tjs
 * @date 2019/10/17 19:14
 * @cst_do HTTP心跳请求回调接口
 * 影响范围：
 * 备注：
 * @cst_end
 */
interface IHttpBeatView : IBaseView {

    fun httpBeatConnectSuccess(resBeatConnectBean: ResBeatConnectBean)

    fun httpBeatConnectFailure(error: Throwable)

}