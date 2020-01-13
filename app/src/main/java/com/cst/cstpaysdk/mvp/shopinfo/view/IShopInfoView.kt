package com.cst.cstpaysdk.mvp.shopinfo.view

import com.cst.cstpaysdk.base.IBaseView
import com.cst.cstpaysdk.bean.ResShopInfoBean

interface IShopInfoView : IBaseView {

    fun getShopInfoSuccess(resShopInfoBean: ResShopInfoBean)

    fun getShopInfoFailure(error: Throwable)
}