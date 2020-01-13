package com.cst.cstpaysdk.mvp.traderecord.view

import com.cst.cstpaysdk.base.IBaseView
import com.cst.cstpaysdk.bean.ResUploadTradeRecordBean

interface IUploadTradeRecordView : IBaseView {

    fun uploadTradeRecordSuccess(resUploadTradeRecordBean: ResUploadTradeRecordBean)

    fun uploadTradeRecordFailure(error: Throwable)
}