package com.cst.cstpaysdk.mvp.verifyInfo.view

import com.cst.cstpaysdk.base.IBaseView
import com.cst.cstpaysdk.bean.ResVerifyInfoBean

interface IVerifyInfoView : IBaseView {

    fun getVerifyInfoSuccess(resVerifyInfoBean: ResVerifyInfoBean)

    fun getVerifyInfoFailure(error: Throwable)
}