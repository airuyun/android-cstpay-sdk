package com.cst.cstpaysdk.mvp.shopinfo.presenter

import android.content.Context
import androidx.annotation.NonNull
import com.cst.cstpaysdk.base.BaseObserver
import com.cst.cstpaysdk.base.BasePresenter
import com.cst.cstpaysdk.bean.ResShopInfoBean
import com.cst.cstpaysdk.mvp.shopinfo.model.IShopInfoModel
import com.cst.cstpaysdk.mvp.shopinfo.model.impl.ShopInfoModelImpl
import com.cst.cstpaysdk.mvp.shopinfo.view.IShopInfoView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ShopInfoPresenter(private val context: Context) : BasePresenter<IShopInfoModel>() {

    private val shopInfoModel: IShopInfoModel = ShopInfoModelImpl()

    fun getShopInfo(shopInfoView: IShopInfoView?) {
        shopInfoModel.getShopInfo(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ResShopInfoBean>(shopInfoView) {

                override fun onNext(@NonNull resShopInfoBean: ResShopInfoBean) {
                    shopInfoView?.getShopInfoSuccess(resShopInfoBean)
                }

                override fun onError(@NonNull e: Throwable) {
                    shopInfoView?.getShopInfoFailure(e)
                }

                override fun onComplete() {
                }
            })
    }

}