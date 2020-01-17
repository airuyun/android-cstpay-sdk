package com.cst.cstpaysdk.mvp.traderecord.view

import com.cst.cstpaysdk.base.IBaseView
import com.cst.cstpaysdk.bean.ResPayRecordBean

interface IPayRecordView : IBaseView {

    fun queryPayRecordSuccess(resPayRecordBean: ResPayRecordBean)

    fun queryPayRecordFailure(error: Throwable)
}