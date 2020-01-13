package com.cst.cstpaysdk.mvp.traderecord.view

import com.cst.cstpaysdk.base.IBaseView
import com.cst.cstpaysdk.bean.ResTradeRecordBean

interface ITradeRecordView : IBaseView {

    fun queryTradeRecordSuccess(resTradeRecordBean: ResTradeRecordBean)

    fun queryTradeRecordFailure(error: Throwable)
}