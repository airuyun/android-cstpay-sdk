package com.cst.cstpaysdk.mvp.traderecord.view

import com.cst.cstpaysdk.base.IBaseView
import com.cst.cstpaysdk.bean.ResUploadTradeRecordBean

interface IUploadPayRecordView : IBaseView {

    fun uploadPayRecordSuccess(resUploadTradeRecordBean: ResUploadTradeRecordBean)

    fun uploadPayRecordFailure(error: Throwable)
}