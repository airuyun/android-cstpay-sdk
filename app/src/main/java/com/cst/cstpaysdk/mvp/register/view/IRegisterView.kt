package com.cst.cstpaysdk.mvp.register.view

import com.cst.cstpaysdk.base.IBaseView
import com.cst.cstpaysdk.bean.ResRegisterBean

interface IRegisterView : IBaseView {

    fun registerSuccess(resRegisterBean: ResRegisterBean)

    fun registerFailure(error: Throwable)
}